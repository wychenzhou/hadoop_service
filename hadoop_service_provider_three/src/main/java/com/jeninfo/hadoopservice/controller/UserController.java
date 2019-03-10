package com.jeninfo.hadoopservice.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jeninfo.hadoopservice.model.User;
import com.jeninfo.hadoopservice.service.UserService;
import com.jeninfo.hadoopservice.vo.BaseController;
import com.jeninfo.hadoopservice.vo.Render;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chenzhou
 */
@RestController
@RequestMapping(value = "/api/admin/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 查询全部
     *
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/select/all", method = RequestMethod.GET)
    public Render selectAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        IPage<User> userIPage = userService.selectByPage(page, pageSize);
        return this.renderPage(userIPage.getRecords(), page, pageSize, userIPage.getTotal(), 0x111, "获取成功");
    }

    /**
     * 服务发现
     *
     * @return
     */
    @RequestMapping(value = "/select/discovery", method = RequestMethod.GET)
    public Object discovery() {
        List<String> list = discoveryClient.getServices();
        System.out.println(list);
        List<ServiceInstance> srvList = discoveryClient.getInstances("HADOOP-SERVICE-PROVIDER");
        for (ServiceInstance element : srvList) {
            System.out.println(element.getServiceId() + "\t" + element.getHost() + "\t" + element.getPort() + "\t"
                    + element.getUri());
        }
        return this.discoveryClient;
    }
}
