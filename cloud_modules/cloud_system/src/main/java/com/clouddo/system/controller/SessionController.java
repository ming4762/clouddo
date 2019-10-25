package com.clouddo.system.controller;

import com.clouddo.commons.common.util.message.R;
import com.clouddo.system.model.UserOnline;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

//imports com.clouddo.system.service.SessionService;

@RequestMapping("/sys/online")
@Controller
public class SessionController {
//	@Autowired
//	SessionService sessionService;

	@GetMapping()
	public String online() {
		return "system/online/online";
	}

	@ResponseBody
	@RequestMapping("/list")
	public List<UserOnline> list() {
		return null;
//		return sessionService.list();
	}

	@ResponseBody
	@RequestMapping("/forceLogout/{sessionId}")
	public R forceLogout(@PathVariable("sessionId") String sessionId, RedirectAttributes redirectAttributes) {
		try {
//			sessionService.forceLogout(sessionId);
			return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}

	}

//	@ResponseBody
//	@RequestMapping("/sessionList")
//	public Collection<Session> sessionList() {
//		return sessionService.sessionList();
//	}


}
