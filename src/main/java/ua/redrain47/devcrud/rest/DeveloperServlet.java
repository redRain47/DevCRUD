package ua.redrain47.devcrud.rest;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import ua.redrain47.devcrud.exceptions.DbConnectionIssueException;
import ua.redrain47.devcrud.exceptions.DeletingReferencedRecordException;
import ua.redrain47.devcrud.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.devcrud.messages.ResponseMessages;
import ua.redrain47.devcrud.model.Account;
import ua.redrain47.devcrud.model.Developer;
import ua.redrain47.devcrud.model.Skill;
import ua.redrain47.devcrud.service.AccountService;
import ua.redrain47.devcrud.service.DeveloperService;
import ua.redrain47.devcrud.service.SkillService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@WebServlet(name = "DeveloperServlet", urlPatterns = "/api/v1/developers")
public class DeveloperServlet extends HttpServlet {
    private Gson gson;
    private SkillService skillService;
    private AccountService accountService;
    private DeveloperService developerService;

    public DeveloperServlet() {
        try {
            this.skillService = new SkillService();
            this.accountService = new AccountService();
            this.developerService = new DeveloperService();
        } catch (DbConnectionIssueException e) {
            log.error("DeveloperServlet -> " + e);
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
                    response.sendError(400, ResponseMessages.INVALID_PARAMETER_TYPE_TEXT);
            }
        }

        log.debug("Request to create (POST)");

        try {
            Developer developer = gson.fromJson(request.getReader(),
                    Developer.class);

            if (isValidDeveloper(developer)) {
                developerService.addData(developer);
            } else {
                response.sendError(400, ResponseMessages.INVALID_REQUEST_BODY_TEXT);
            }
        } catch (SuchEntityAlreadyExistsException e) {
            response.sendError(400, e.getMessage());
        } catch (DbConnectionIssueException e) {
            response.sendError(506, e.getMessage());
        } catch (JsonIOException | JsonSyntaxException e) {
            response.sendError(400, ResponseMessages.JSON_PARSING_ERROR_TEXT);
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
                writer.println(gson.toJson(developerService.getAllData()));
            } else {
                log.debug("Request to get by id");
                Developer developer = developerService
                        .getDataById(Long.parseLong(request
                                .getParameter("id")));

                writer.println(gson.toJson(developer));
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
            Developer developer = gson.fromJson(request.getReader(),
                    Developer.class);

            if (!isValidDeveloper(developer)) {
                response.sendError(400, ResponseMessages.INVALID_REQUEST_BODY_TEXT);
                return;
            }

            if (developerService.getDataById(developer.getId()) != null) {
                developerService.updateDataById(developer);
            } else {
                response.sendError(400, ResponseMessages.NO_SUCH_ID_TEXT);
            }
        } catch (DbConnectionIssueException e) {
            response.sendError(506, e.getMessage());
        } catch (SuchEntityAlreadyExistsException e) {
            response.sendError(400, e.getMessage());
        } catch (JsonIOException | JsonSyntaxException e) {
            response.sendError(400, ResponseMessages.JSON_PARSING_ERROR_TEXT);
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
                response.sendError(400, ResponseMessages.INVALID_ID_PARAMETER_TEXT);
            } else {
                Long deletedId = Long.parseLong(request
                        .getParameter("id"));

                if (developerService.getDataById(deletedId) != null) {
                    developerService.deleteDataById(deletedId);
                } else {
                    response.sendError(400, ResponseMessages.NO_SUCH_ID_TEXT);
                }
            }
        } catch (DeletingReferencedRecordException e) {
            response.sendError(400, e.getMessage());
        } catch (DbConnectionIssueException e) {
            response.sendError(506, e.getMessage());
        }
    }

    private boolean isValidDeveloper(Developer developer) throws DbConnectionIssueException {
        return developer != null && developer.getFirstName() != null
                && developer.getLastName() != null
                && isValidSkillSet(developer.getSkillSet())
                && isValidAccountId(developer.getAccount());
    }

    private boolean isValidAccountId(Account account) throws DbConnectionIssueException {
        return account == null
                || accountService.getDataById(account.getId()) != null;
    }

    private boolean isValidSkillSet(Set<Skill> skillSet)
            throws DbConnectionIssueException {
        if (skillSet == null) {
            return false;
        }

        Set<Long> originalIds = new HashSet<>();

        for (Skill skill : skillSet) {
            if (!originalIds.add(skill.getId())
                    || skillService.getDataById(skill.getId()) == null) {
                return false;
            }
        }

        return true;
    }
}
