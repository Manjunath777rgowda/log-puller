package com.manjunath.logpuller.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.manjunath.logpuller.constants.RestEndPoints;
import com.manjunath.logpuller.constants.ViewConstants;
import com.manjunath.logpuller.exceptions.DataException;
import com.manjunath.logpuller.service.log.LogService;
import com.manjunath.logpuller.utils.FileUtil;
import com.manjunath.logpuller.utils.NullEmptyUtils;

@Controller
public class MainController {

    @Autowired
    private Environment environment;

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
    public String getLogs( @RequestParam( value = "logId" ) String logId, RedirectAttributes redirectAttributes,
            Model model )
    {
        try
        {
            if( NullEmptyUtils.isNullOrEmpty(logId) )
                throw new DataException("Log Id can not be empty", HttpStatus.BAD_REQUEST);

            model.addAttribute("nodes", logService.getLogs(logId));
            return ViewConstants.DASHBOARD;
        }
        catch( DataException e )
        {
            redirectAttributes.addFlashAttribute("error", e.getErrorMessage());
            return "redirect:/dashboard";
        }
    }

    @GetMapping( RestEndPoints.DOWNLOAD )
    public String download( @RequestParam( value = "logId" ) String logId, RedirectAttributes redirectAttributes,
            HttpServletResponse response )
    {
        try
        {
            if( NullEmptyUtils.isNullOrEmpty(logId) )
                throw new DataException("Log Id can not be empty", HttpStatus.BAD_REQUEST);

            String appFolder = environment.getProperty("app.folder");
            File folder = new File(appFolder + logId);

            if( !folder.exists() )
                logService.getLogs(logId);

            if( !folder.exists() )
                throw new DataException("Invalid Log Id", HttpStatus.BAD_REQUEST);

            //zip file
            File zipFile = FileUtil.zipFile(folder.getAbsolutePath());

            if( zipFile.exists() )
            {

                //get the mimetype
                String mimeType = URLConnection.guessContentTypeFromName(zipFile.getName());
                if( mimeType == null )
                {
                    //unknown mimetype so set the mimetype to application/octet-stream
                    mimeType = "application/octet-stream";
                }

                response.setContentType(mimeType);

                /**
                 * In a regular HTTP response, the Content-Disposition response header is a header indicating if the
                 * content is expected to be displayed inline in the browser, that is, as a Web page or as part of a
                 * Web page, or as an attachment, that is downloaded and saved locally.
                 *
                 */

                /**
                 * Here we have mentioned it to show inline
                 */
                response.setHeader("Content-Disposition",
                        String.format("inline; filename=\"" + zipFile.getName() + "\""));

                //Here we have mentioned it to show as attachment
                //response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

                response.setContentLength((int) zipFile.length());

                InputStream inputStream = new BufferedInputStream(new FileInputStream(zipFile));

                FileCopyUtils.copy(inputStream, response.getOutputStream());

            }
            return ViewConstants.DASHBOARD;
        }
        catch( DataException e )
        {
            redirectAttributes.addFlashAttribute("error", e.getErrorMessage());
            return "redirect:/dashboard";
        }
        catch( Exception e )
        {
            redirectAttributes.addFlashAttribute("error", "Error While Downloading");
            return "redirect:/dashboard";
        }
    }

    @GetMapping( RestEndPoints.LOGIN )
    public String login()
    {
        return ViewConstants.LOGIN;
    }

}
