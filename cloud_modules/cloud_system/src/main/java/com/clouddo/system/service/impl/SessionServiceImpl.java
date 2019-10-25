package com.clouddo.system.service.impl;//package com.clouddo.system.service.impl;
//
//imports com.clouddo.system.model.User;
//imports com.clouddo.system.model.UserOnline;
//imports com.clouddo.system.service.SessionService;
//imports org.apache.shiro.session.Session;
//imports org.apache.shiro.session.mgt.eis.SessionDAO;
//imports org.apache.shiro.subject.SimplePrincipalCollection;
//imports org.apache.shiro.subject.support.DefaultSubjectContext;
//imports org.springframework.beans.factory.annotation.Autowired;
//imports org.springframework.stereotype.Service;
//
//imports java.util.ArrayList;
//imports java.util.Collection;
//imports java.util.List;
//
///**
// * 待完善
// *
// * @author bootdo
// */
//@Service
//public class SessionServiceImpl implements SessionService {
//    private final SessionDAO sessionDAO;
//
//    @Autowired
//    public SessionServiceImpl(SessionDAO sessionDAO) {
//        this.sessionDAO = sessionDAO;
//    }
//
//    @Override
//    public List<UserOnline> list() {
//        List<UserOnline> list = new ArrayList<>();
//        Collection<Session> sessions = sessionDAO.getActiveSessions();
//        for (Session session : sessions) {
//            UserOnline userOnline = new UserOnline();
//            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
//                continue;
//            } else {
//                SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) session
//                        .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
//                User userDO = (User) principalCollection.getPrimaryPrincipal();
//                userOnline.setUsername(userDO.getUsername());
//            }
//            userOnline.setId((String) session.getId());
//            userOnline.setHost(session.getHost());
//            userOnline.setStartTimestamp(session.getStartTimestamp());
//            userOnline.setLastAccessTime(session.getLastAccessTime());
//            userOnline.setTimeout(session.getTimeout());
//            list.add(userOnline);
//        }
//        return list;
//    }
//
//    @Override
//    public List<User> listOnlineUser() {
//        List<User> list = new ArrayList<>();
//        User userDO;
//        Collection<Session> sessions = sessionDAO.getActiveSessions();
//        for (Session session : sessions) {
//            SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
//            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
//                continue;
//            } else {
//                principalCollection = (SimplePrincipalCollection) session
//                        .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
//                userDO = (User) principalCollection.getPrimaryPrincipal();
//                list.add(userDO);
//            }
//        }
//        return list;
//    }
//
//    @Override
//    public Collection<Session> sessionList() {
//        return sessionDAO.getActiveSessions();
//    }
//
//    @Override
//    public boolean forceLogout(String sessionId) {
//        Session session = sessionDAO.readSession(sessionId);
//        session.setTimeout(0);
//        return true;
//    }
//}
