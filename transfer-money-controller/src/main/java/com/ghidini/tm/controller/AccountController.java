package com.ghidini.tm.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ghidini.tm.domain.dto.AccountDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Account Operations", tags = { "ACCOUNT" })
@Path("account")
public class AccountController {
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Add a new account", response = Response.class)
	public void addAccount() {
		
	}
	
	@DELETE
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Delete a transaction", response = Response.class)
	public void deleteAccount() {
		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find account by id", response = AccountDTO.class)
	public void getAccountById() {
		
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all accounts", response = List.class)
	public void getAllAccounts() {
		
	}

}
