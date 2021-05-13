package com.dwj.vblogold.controller;

import com.dwj.vblogold.response.JsonResponse;
import com.dwj.vblogold.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@RestController("TimelineController")
@RequestMapping("/api/timeline")
public class TimelineController {

    @Autowired
    private TimelineService timelineService;

    @ResponseBody
    @GetMapping("/getTimeline")
    public JsonResponse getTags(@RequestParam(name = "offset",required = true) int offset, @RequestParam(name = "limit",required = true) int limit) {
        return JsonResponse.success(timelineService.queryTimelineList(offset, limit));
    }

}
