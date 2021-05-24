package com.company.pm.personalservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.personalservice.Project;
import com.company.pm.personalservice.web.ProjectController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class ProjectRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Project> {
    
    public ProjectRepresentationModelAssembler() {
        super(ProjectController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "projects";
    }
    
    @Override
    protected String getEntityId(EntityModel<Project> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(ProjectController.class).getProjects(exchange), exchange);
    }
}
