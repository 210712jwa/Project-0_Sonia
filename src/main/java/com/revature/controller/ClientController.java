package com.revature.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.application.Application;
import com.revature.dto.AddOrEditClientDTO;
import com.revature.model.Client;
import com.revature.service.ClientService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ClientController implements Controller {

	private ClientService clientService;
	private static Logger logger = LoggerFactory.getLogger(ClientController.class);
	public ClientController() {
		this.clientService = new ClientService();
	}

	private Handler getAllClients = (ctx) -> {

		List<Client> clients = clientService.getAllClients();
logger.info("Get all clients list of clients:["+clients.toString()+"]");
		ctx.status(200); 
		ctx.json(clients);
	};

	private Handler getClientById = (ctx) -> {
	
		String clientid = ctx.pathParam("clientid");
		
		logger.info("Get all client_id of clients:["+clientid.toString()+"]");
		
		Client client = clientService.getClientById(clientid);
		
		ctx.json(client);
	};

	private Handler addClient = (ctx) -> {
		AddOrEditClientDTO clientToAdd = ctx.bodyAsClass(AddOrEditClientDTO.class);

		Client addedClient = clientService.addClient(clientToAdd);
		ctx.json(addedClient);
	};
	private Handler editClient = (ctx) -> {
		AddOrEditClientDTO clientToEdit = ctx.bodyAsClass(AddOrEditClientDTO.class);

		String clientId = ctx.pathParam("clientid");
		Client editedClient = clientService.editClient(clientId, clientToEdit);

		ctx.json(editedClient);
	};
	private Handler deleteClient = (ctx) -> {
		String clientId = ctx.pathParam("clientid");

		clientService.deleteClient(clientId);
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/client", getAllClients);
		app.get("/client/:clientid", getClientById);
		app.post("/client", addClient);
		app.put("/client/:clientid", editClient);
		app.delete("/client/:clientid", deleteClient);
	}
}


