package com.manjunath.logpuller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.manjunath.logpuller.constants.RestEndPoints;
import com.manjunath.logpuller.constants.ViewConstants;
import com.manjunath.logpuller.exceptions.DataException;
import com.manjunath.logpuller.representation.request.GrayLogOutput;
import com.manjunath.logpuller.service.log.LogService;
import com.manjunath.logpuller.utils.NullEmptyUtils;

@Controller
public class MainController {

    @Autowired
    private LogService logService;

    @GetMapping( "/" )
    public String home()
    {
        return ViewConstants.DASHBOARD;
    }

    @GetMapping( RestEndPoints.DASHBOARD )
    public String getDashboard()
    {
        return ViewConstants.DASHBOARD;
    }

    @GetMapping( RestEndPoints.GET_LOG )
    public String getLogs( @RequestParam( value = "logId" ) String logId, RedirectAttributes redirectAttributes )
    {
        try
        {
            if( NullEmptyUtils.isNullOrEmpty(logId) )
                throw new DataException("Log Id can not be empty", HttpStatus.BAD_REQUEST);

            GrayLogOutput grayLogOutput = logService.getLogs(logId);
            return ViewConstants.DASHBOARD;
        }
        catch( DataException e )
        {
            redirectAttributes.addFlashAttribute("error", e.getErrorMessage());
            return "redirect:/dashboard";
        }
    }

    @GetMapping( RestEndPoints.LOGIN )
    public String login()
    {
        return ViewConstants.LOGIN;
    }

}
