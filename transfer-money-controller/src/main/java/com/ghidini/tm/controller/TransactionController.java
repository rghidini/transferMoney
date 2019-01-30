package com.ghidini.tm.controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ghidini.tm.domain.dto.TransactionDTO;
import com.ghidini.tm.exceptions.DBCommitException;
import com.ghidini.tm.exceptions.IdNotFoundException;
import com.ghidini.tm.service.interfaces.ITransactionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Do Transaction", tags = { "TRANSACTION" })
@Path("transaction")
public class TransactionController {
	
	@Inject
	private ITransactionService service;

	@POST
	@Path("/do")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Do the transaction", response = Response.class)
	public Response doTransaction(TransactionDTO transaction) {
		Response response = null;
		try {
			service.addTransaction(transaction);
			response = Response.status(Response.Status.CREATED).build();
		} catch (IdNotFoundException id) {
			response = Response.status(Response.Status.NOT_FOUND).entity(id.getMessage()).build();
		} catch (IllegalArgumentException | DBCommitException e) {
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return response;
	}

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all transactions", response = TransactionDTO.class)
	public void getAllTransactions() {
		service.getAllTransaction();
	}

}
