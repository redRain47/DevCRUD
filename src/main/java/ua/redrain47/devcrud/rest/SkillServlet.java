package ua.redrain47.devcrud.rest;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.redrain47.devcrud.exceptions.DbConnectionIssueException;
import ua.redrain47.devcrud.exceptions.DeletingReferencedRecordException;
import ua.redrain47.devcrud.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.devcrud.model.Skill;
import ua.redrain47.devcrud.service.SkillService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static ua.redrain47.devcrud.messages.ResponseMessages.*;

@Slf4j
@WebServlet(name = "SkillServlet", urlPatterns = "/api/v1/skills")
public class SkillServlet extends HttpServlet {
    private Gson gson;
    private SkillService skillService;

    public SkillServlet() {
        try {
            AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext();

            appCtx.scan("ua.redrain47.devcrud.service");
            appCtx.refresh();
            this.skillService = appCtx.getBean(SkillService.class);
        } catch (BeansException e) {
            log.error("SkillServlet -> " + e);
            e.printStackTrace();
        }
        this.gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        if(request.getParameter("type") != null) {
            log.debug("Request with type parameter (POST)");
            switch (request.getParameter("type")) {
                case "create":
                    break;
                case "read":
                    this.doGet(request, response);
                    return;
                case "update":
                    this.doPut(request, response);
                    return;
                case "delete":
                    this.doDelete(request, response);
                    return;
                default:
                    log.warn("Invalid parameter type in POST request");
                    response.sendError(400, INVALID_PARAMETER_TYPE_TEXT);
            }
        }

        log.debug("Request to create (POST)");

        try {
            Skill skill = gson.fromJson(request.getReader(), Skill.class);

            if (isValidSkill(skill)) {
                skillService.addData(skill);
            } else {
                response.sendError(400, INVALID_REQUEST_BODY_TEXT);
            }
        } catch (DbConnectionIssueException e) {
            response.sendError(506, e.getMessage());
        } catch (SuchEntityAlreadyExistsException e) {
            response.sendError(419, e.getMessage());
        } catch (JsonIOException | JsonSyntaxException e) {
            response.sendError(400, JSON_PARSING_ERROR_TEXT);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        log.debug("Request to read (GET)");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        try {
            if (request.getParameter("id") == null
                    || !request.getParameter("id").matches("\\d+")) {
                log.debug("Request to get all");
                writer.println(gson.toJson(skillService.getAllData()));
            } else {
                log.debug("Request to get by id");
                Skill skill = skillService
                        .getDataById(Long.parseLong(request
                                .getParameter("id")));

                writer.println(gson.toJson(skill));
            }

            log.debug("Send JSON response");
        } catch (DbConnectionIssueException e) {
            response.sendError(506, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        log.debug("Request to update (PUT)");

        try {
            Skill skill = gson.fromJson(request.getReader(),
                    Skill.class);

            if (!isValidSkill(skill)) {
                response.sendError(400, INVALID_REQUEST_BODY_TEXT);
                return;
            }

            if (skillService.getDataById(skill.getId()) != null) {
                skillService.updateDataById(skill);
            } else {
                response.sendError(400, NO_SUCH_ID_TEXT);
            }
        } catch (DbConnectionIssueException e) {
            response.sendError(506, e.getMessage());
        } catch (SuchEntityAlreadyExistsException e) {
            response.sendError(400, e.getMessage());
        } catch (JsonIOException | JsonSyntaxException e) {
            response.sendError(400, JSON_PARSING_ERROR_TEXT);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        log.debug("Request to delete (DELETE)");

        try {
            if (request.getParameter("id") == null
                    || !request.getParameter("id").matches("\\d+")) {
                log.warn("Invalid id parameter in DELETE request");
                response.sendError(400, INVALID_ID_PARAMETER_TEXT);
            } else {
                Long deletedId = Long.parseLong(request
                        .getParameter("id"));

                if (skillService.getDataById(deletedId) != null) {
                    skillService.deleteDataById(deletedId);
                } else {
                    response.sendError(400, NO_SUCH_ID_TEXT);
                }
            }
        } catch (DeletingReferencedRecordException e) {
            response.sendError(400, e.getMessage());
        } catch (DbConnectionIssueException e) {
            response.sendError(506, e.getMessage());
        }
    }

    private boolean isValidSkill(Skill skill) {
        return skill != null && skill.getName() != null;
    }
}
