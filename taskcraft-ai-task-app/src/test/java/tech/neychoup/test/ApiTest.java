package tech.neychoup.test;

import dev.langchain4j.model.chat.ChatLanguageModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tech.neychoup.domain.task.adapter.port.ITaskPort;
import tech.neychoup.domain.task.model.aggregate.AssignmentCompletion;
import tech.neychoup.domain.task.model.entity.Task;

import java.io.IOException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Value("${ai-api.glm-url}")
    private String glmUrl;

    @Value("${ai-api.glm-key}")
    private String glmKey;

    @Test
    public void test() {
        log.info("测试完成");
    }

    @Autowired
    private ITaskPort taskPort;

    @Autowired
    private ChatLanguageModel chatLanguageModel;

    @Test
    public void test_chat() {
        String res = chatLanguageModel.generate("你好");
        System.out.println(res);
    }

    @Test
    public void test_taskPort_verify_Tasks() {
        // 模拟任务
        Task task = new Task();
        task.setId(1L);
        task.setTaskName("优化合约性能");
        task.setDescription("学习优化合约性能并减少Gas费用的技巧。");
        // task.setDifficulty(4L);
        task.setTokenReward(400L);
        task.setExperienceReward(350L);
        task.setAssignment("优化一个已有的合约，并对比优化前后的Gas使用情况。");

        // 作业答案
        String content = "原合约代码:\n" +
                "                    pragma solidity ^0.8.0;\n" +
                "\n" +
                "                    contract GasInefficient {\n" +
                "                        uint256[] public data;\n" +
                "\n" +
                "                        function addElement(uint256 value) public {\n" +
                "                            data.push(value);\n" +
                "                        }\n" +
                "\n" +
                "                        function calculateSum() public view returns (uint256) {\n" +
                "                            uint256 sum = 0;\n" +
                "                            for (uint256 i = 0; i < data.length; i++) {\n" +
                "                                sum += data[i];\n" +
                "                            }\n" +
                "                            return sum;\n" +
                "                        }\n" +
                "                    }\n" +
                "                    ```\n" +
                "\n" +
                "                    优化后的合约代码:\n" +
                "                    ```\n" +
                "                    pragma solidity ^0.8.0;\n" +
                "\n" +
                "                    contract GasOptimized {\n" +
                "                        uint256[] public data;\n" +
                "                        uint256 public totalSum;\n" +
                "\n" +
                "                        function addElement(uint256 value) public {\n" +
                "                            data.push(value);\n" +
                "                            totalSum += value; // 利用状态变量缓存总和\n" +
                "                        }\n" +
                "\n" +
                "                        function calculateSum() public view returns (uint256) {\n" +
                "                            return totalSum; // 直接返回缓存的总和\n" +
                "                        }\n" +
                "                    }\n" +
                "\n" +
                "                    优化效果:\n" +
                "                    1. 原合约的 `calculateSum` 函数在每次调用时，需要遍历整个数组，消耗 O(n) 的 Gas。\n" +
                "                    2. 优化后，`calculateSum` 直接返回缓存的 `totalSum`，Gas 消耗为 O(1)。\n" +
                "                    3. 测试对比显示，优化后的 `calculateSum` 函数的 Gas 消耗减少了约 80%。";
        AssignmentCompletion assignmentCompletion;
        try {
            assignmentCompletion = taskPort.verifyTaskFinished(task, content);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 输出验证结果
        log.info("是否完成任务：{}", assignmentCompletion);
    }
}
