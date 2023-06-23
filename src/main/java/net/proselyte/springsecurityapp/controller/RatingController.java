package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.model.RatingTask;
import net.proselyte.springsecurityapp.service.RatingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class RatingController {
    @Autowired
    private RatingTaskService ratingTaskService;
    @RequestMapping(value = "/ratings", method = RequestMethod.POST)
    public String showRatings(Model model) {
        List<RatingTask> listRatingTask = ratingTaskService.findAll();
        model.addAttribute("rating", listRatingTask);
        return "ratings";
    }

    @RequestMapping(value = "/rating", method = RequestMethod.POST)
    public String ratingWork(String ratingg, String NameTasker, String uidDoc_8) {
        RatingTask ratingTask = new RatingTask();
        ratingTask.setRating(ratingg);
        ratingTask.setUidDoc(uidDoc_8);
        ratingTask.setNameTask(NameTasker);
        ratingTaskService.save(ratingTask);
        return "redirect:/tasks";
    }
}
