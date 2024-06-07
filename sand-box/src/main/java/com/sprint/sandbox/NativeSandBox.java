package com.sprint.sandbox;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.dfa.WordTree;
import com.sprint.sandbox.model.ExecuteCodeRequest;
import com.sprint.sandbox.model.ExecuteCodeResponse;
import com.sprint.sandbox.model.ExecuteMessage;
import com.sprint.sandbox.model.JudgeInfo;
import com.sprint.sandbox.utils.ProcessUtils;

public class NativeSandBox implements CodeSandbox {
    public static final long TIME_LIMIT = 1000L;
    public static final ArrayList<String> blackList=new ArrayList<>(Arrays.asList("Runtime","ProcessBuilder","System","File","FileInputStream","FileOutputStream","RandomAccessFile","ObjectInputStream","ObjectOutputStream","BufferedReader","BufferedWriter","PrintWriter","PrintStream","Scanner","DataInputStream","DataOutputStream","ZipInputStream","ZipOutputStream","JarInputStream","JarOutputStream","GZIPInputStream","GZIPOutputStream","InflaterInputStream","DeflaterOutputStream","FileReader","FileWriter","CharArrayReader","CharArrayWriter","StringReader","StringWriter","PipedInputStream","PipedOutputStream","PipedReader","PipedWriter","SequenceInputStream","LineNumberReader","PushbackReader","PushbackInputStream","PrintStream","PrintWriter"));
    public static final WordTree tree;

    static{
        tree = new WordTree();
        tree.addWords(blackList);
    }
    public static void main(String[] args) {
        NativeSandBox sandBox = new NativeSandBox();

        //String code = ResourceUtil.readStr("testcode/args/Main.java", StandardCharsets.UTF_8);
        //String code=ResourceUtil.readStr("errorcode/memoryExceed.java",StandardCharsets.UTF_8);
        //String code=ResourceUtil.readStr("errorcode/TimeExceed.java",StandardCharsets.UTF_8);
        String code=ResourceUtil.readStr("errorcode/ReadFile.java",StandardCharsets.UTF_8);
        ExecuteCodeRequest request = new ExecuteCodeRequest();
        request.setInputList(Arrays.asList("1 2", "3 42"));
        request.setCode(code);
        request.setLanguage("java");
        ExecuteCodeResponse executeCodeResponse = sandBox.executeCode(request);

        //System.out.println(code);


    }


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();

        // 0 check the code
        String match = tree.match(code);
        if(StrUtil.isNotBlank(match)){
            ExecuteCodeResponse response=new ExecuteCodeResponse();
            response.setStatus(2);
            response.setMessage("code contains black list word:"+match);
            System.out.println("code contains black list word:"+match);
            return response;
        }

        // 1 create a tmpCode folder
        String userdir = System.getProperty("user.dir");
        String globalPath = userdir + File.separator + "tmpCode";
        List<ExecuteMessage> messageList=new ArrayList<>();
        if (!FileUtil.exist(globalPath)) {
            FileUtil.mkdir(globalPath);
        }
        String userCodeParentPath = globalPath + File.separator + UUID.randomUUID();
        String userCodePath = userCodeParentPath + File.separator + "Main.java";
        File userCodeFile = FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);

        //compile the file
        String compileCmd = String.format("javac -encoding utf-8 %s", userCodeFile.getAbsolutePath());

        try {
            Process process = Runtime.getRuntime().exec(compileCmd);
            ExecuteMessage m = ProcessUtils.runProcess(process, "compile");
            System.out.println(m);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

            //run the code

        for (String s:inputList){
            StopWatch watch=new StopWatch();
            String runCmd = String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main %s", userCodeParentPath,s);

            try {

                Process process = Runtime.getRuntime().exec(runCmd);
                new Thread(()->{
                    try {
                        Thread.sleep(TIME_LIMIT);
                        process.destroy();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                ExecuteMessage m = ProcessUtils.runProcess(process, "run");
                messageList.add(m);
                System.out.println(m);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // 6 deal with the result
        ExecuteCodeResponse response=new ExecuteCodeResponse();
        List<String> outputList=new ArrayList<>();
        long maxTime=0;
        for(ExecuteMessage m:messageList){
            String errorMessage=m.getErrorMessage();
            long time=m.getTime();
            maxTime = Math.max(maxTime, time);

            if(StrUtil.isNotBlank(m.getErrorMessage())){
                response.setMessage(errorMessage);
                //user code error
                response.setStatus(3);
                break;
            }
            outputList.add(m.getMessage());

        }
        if(outputList.size()==messageList.size()){
            response.setStatus(1);
        }
        response.setOutputList(outputList);
        JudgeInfo judgeInfo=new JudgeInfo();
        judgeInfo.setTime(maxTime);
        response.setJudgeInfo(judgeInfo);


        // delete the tmpCode
        if(userCodeFile.getParentFile()!=null){
            boolean del =FileUtil.del(userCodeParentPath);
            System.out.println("del"+(del?"success":"failure"));

        }


        return response;
    }

}
