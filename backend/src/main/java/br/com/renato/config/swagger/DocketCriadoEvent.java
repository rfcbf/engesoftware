package br.com.renato.config.swagger;

import org.springframework.context.ApplicationEvent;
import springfox.documentation.spring.web.plugins.Docket;

public class DocketCriadoEvent extends ApplicationEvent {

    private final transient Docket docket;

    public DocketCriadoEvent(Object source, Docket docket) {
        super(source);
        this.docket = docket;
    }

    public Docket getDocket() {
        return docket;
    }
}
