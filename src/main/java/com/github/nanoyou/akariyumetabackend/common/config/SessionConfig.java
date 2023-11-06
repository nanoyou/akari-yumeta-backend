package com.github.nanoyou.akariyumetabackend.common.config;

import com.github.nanoyou.akariyumetabackend.common.constant.SessionConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.HashMap;


@Configuration
@EnableSpringHttpSession
public class SessionConfig {
    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return new HeaderHttpSessionIdResolver(SessionConst.SESSION_HEAD);
    }

    @Bean
    public SessionRepository<MapSession> sessionRepository() {
        return new MapSessionRepository(new HashMap<>()) {
            @Override
            public MapSession createSession() {
                var sessionId =
                        (String) RequestContextHolder
                                .currentRequestAttributes()
                                .getAttribute(SessionConfig.class.getName() + "SessionIdAttr", 0);
                final var session = super.createSession();
                if (sessionId != null) {
                    session.setId(sessionId);
                }
                return session;
            }
        };
    }
}
