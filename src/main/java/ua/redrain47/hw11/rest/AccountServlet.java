package ua.redrain47.hw11.rest;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import ua.redrain47.hw11.exceptions.DbConnectionIssueException;
import ua.redrain47.hw11.exceptions.DeletingReferencedRecordException;
import ua.redrain47.hw11.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.hw11.model.Account;
import ua.redrain47.hw11.service.AccountService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@WebServlet(name = "AccountServlet", urlPatterns = "/api/v1/accounts")
public class AccountServlet extends HttpServlet {
    private Gson gson;
    private AccountService accountService;

    public AccountServlet() {
        try {
            this.accountService = new AccountService();
        } catch (DbConnectionIssueException e) {
            log.error("AccountServlet -> " + e);
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
                    // TODO: move error codes to separate entity
                    response.sendError(400, "Invalid parameter type");
            }
        }

        log.debug("Request to create (POST)");

        try {
            Account account = gson.fromJson(request.getReader(),
                    Account.class);

            if (isValidAccount(account)) {
                accountService.addData(account);
            } else {
                response.sendError(400, "Invalid request body");
            }
        } catch (DbConnectionIssueException e) {
            response.sendError(506, e.getMessage());
        } catch (SuchEntityAlreadyExistsException e) {
            response.sendError(400, e.getMessage());
        } catch (JsonIOException | JsonSyntaxException e) {
            response.sendError(400, "Json parsing error");
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
                writer.println(gson.toJson(accountService.getAllData()));
            } else {
                log.debug("Request to get by id");
                Account account = accountService
                        .getDataById(Long.parseLong(request
                                .getParameter("id")));

                writer.println(gson.toJson(account));
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
            Account account = gson.fromJson(request.getReader(),
                    Account.class);

            if (!isValidAccount(account)) {
                response.sendError(400, "Invalid request body");
                return;
            }

            if (accountService.getDataById(account.getId()) != null) {
                accountService.updateDataById(account);
            } else {
                response.sendError(400, "Such id doesn't exist");
            }
        } catch (DbConnectionIssueException e) {
            response.sendError(506, e.getMessage());
        } catch (SuchEntityAlreadyExistsException e) {
            response.sendError(400, e.getMessage());
        } catch (JsonIOException | JsonSyntaxException e) {
            response.sendError(400, "Json parsing error");
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
                response.sendError(400, "Invalid ID parameter");
            } else {
                Long deletedId = Long.parseLong(request
                        .getParameter("id"));

                if (accountService.getDataById(deletedId) != null) {
                    accountService.deleteDataById(deletedId);
                } else {
                    response.sendError(400, "Such id doesn't exist");
                }
            }
        } catch (DeletingReferencedRecordException e) {
            response.sendError(400, e.getMessage());
        } catch (DbConnectionIssueException e) {
            response.sendError(506, e.getMessage());
        }
    }

    private boolean isValidAccount(Account account) {
        return account != null && account.getEmail() != null
                && account.getAccountStatus() != null;
    }
}
