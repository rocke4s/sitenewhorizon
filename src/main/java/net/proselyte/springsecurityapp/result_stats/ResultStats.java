package net.proselyte.springsecurityapp.result_stats;

import net.proselyte.springsecurityapp.model.Task;
import net.proselyte.springsecurityapp.model.Tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultStats {
    public Map<String, Map<String, Integer>> taskResult(Task task) {
        Map<String, Map<String, Integer>> stats = new HashMap<>();
        for (Tasks tasks : task.getTasks()) {
            String userName = tasks.getTaskPartner();
            Map<String, Integer> userStats = stats.get(userName);
            if (userStats == null) {
                userStats = new HashMap<>();
                stats.put(userName, userStats);
            }
            int count = userStats.getOrDefault(tasks.getTaskStatus(), 0);
            userStats.put(tasks.getTaskStatus(), count + 1);
        }
        return stats;
    }
}
