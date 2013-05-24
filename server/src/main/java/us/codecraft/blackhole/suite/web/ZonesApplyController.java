package us.codecraft.blackhole.suite.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import us.codecraft.blackhole.suite.connector.BlackholeConnector;
import us.codecraft.blackhole.suite.connector.ZonesFileApplyer;
import us.codecraft.blackhole.suite.model.JsonResult;
import us.codecraft.blackhole.suite.util.IPUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: cairne
 * Date: 13-5-12
 * Time: 下午8:53
 */
@Controller
@RequestMapping("apply")
public class ZonesApplyController extends MultiActionController {

    @Autowired
    private ZonesFileApplyer zonesFileApplyer;

    @Autowired
    private BlackholeConnector blackholeConnector;

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object save(@RequestParam("text") String text, HttpServletRequest request) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        zonesFileApplyer.apply(IPUtils.getClientIp(request), text);
        if (blackholeConnector.isConnected()) {
            return JsonResult.success("应用成功！");
        } else {
            return JsonResult.error("应用失败，Blackhole未启动！");
        }
    }
}
