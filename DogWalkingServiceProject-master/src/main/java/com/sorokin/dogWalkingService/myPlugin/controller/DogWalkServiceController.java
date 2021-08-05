package com.sorokin.dogWalkingService.myPlugin.controller;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.sorokin.dogWalkingService.myPlugin.controller.impl.*;
import com.sorokin.dogWalkingService.myPlugin.models.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Path("/dogWalkingService")
public class DogWalkServiceController {


    private final ActiveObjects ao;

    public DogWalkServiceController(ActiveObjects ao) {
        this.ao = ao;
    }

    ////////////////////////////////////////////
    //Rest for clients
    private ClientImpl clientImpl;

    public ClientImpl getClientImpl() {
        if (clientImpl == null) {
            clientImpl = ClientImpl.create(ao);
        }
        return clientImpl;
    }


    @POST
    @Path("/createClient")
    public Response createClient(Client client) {
        return Response.ok(getClientImpl().createClient(client)).build();
    }

    @GET
    @Path("/client")
    public Response getClientByUniqueId(@QueryParam("uniqueId") String uniqueId) {
        return Response.ok(getClientImpl().getClientByUniqueId(uniqueId)).build();
    }

    @GET
    @Path("/allClients")
    public Response getAllClients() {
        return Response.ok(getClientImpl().getAllClients()).build();
    }

    @PUT
    @Path("/updateClient")
    public Response updateClient(Client client) {
        return Response.ok(getClientImpl().updateClient(client)).build();
    }

    @DELETE
    @Path("/deleteClient")
    public Response deleteClientByUniqueId(@QueryParam("uniqueId") String uniqueId) {
        return Response.ok(getClientImpl().deleteClient(uniqueId)).build();
    }

    ///////////////////////////////////////
    //Rest for dog
    private DogImpl dogImpl;

    public DogImpl getDogImpl() {
        if (dogImpl == null) {
            dogImpl = DogImpl.create(ao);
        }
        return dogImpl;
    }

    @POST
    @Path("/createDog")
    public Response createDog(Dog dog) {
        return Response.ok(getDogImpl().createDog(dog)).build();
    }

    @GET
    @Path("/dog")
    public Response getDogByUniqueId(@QueryParam("uniqueId") String uniqueId) {
        return Response.ok(getDogImpl().getDogByUniqueId(uniqueId)).build();
    }

    @GET
    @Path("/ownerDogs")
    public Response getDogByOwnerId(@QueryParam("ownerId") String ownerId){
        return Response.ok(getDogImpl().getDogByOwnerId(ownerId)).build();
    }

    @GET
    @Path("/allDogs")
    public Response getAllDogs() {
        return Response.ok(getDogImpl().getAllDogs()).build();
    }

    @PUT
    @Path("/fullUpdateDog")
    public Response fullUpdateDog(Dog dog) {
        return Response.ok(getDogImpl().fullUpdateDog(dog)).build();
    }

    @PUT
    @Path("/updateDog")
    public Response updateDog(Dog dog) {
        return Response.ok(getDogImpl().updateDog(dog)).build();
    }

    @DELETE
    @Path("/deleteDog")
    public Response deleteDogByUniqueId(@QueryParam("uniqueId") String uniqueId) {
        return Response.ok(getDogImpl().deleteDog(uniqueId)).build();
    }

    ////////////////////////////////////////////////////////////
    //Rest for dog-walker
    private DogWalkerImpl dogWalkerImpl;

    public DogWalkerImpl getDogWalkerImpl() {
        if (dogWalkerImpl == null) {
            dogWalkerImpl = DogWalkerImpl.create(ao);
        }
        return dogWalkerImpl;
    }

    @POST
    @Path("/createDogWalker")
    public Response createDogWalker(DogWalker dogWalker) {
        return Response.ok(getDogWalkerImpl().createDogWalker(dogWalker)).build();
    }

    @GET
    @Path("/dogWalker")
    public Response getDogWalkerByUniqueId(@QueryParam("uniqueId") String uniqueId) {
        return Response.ok(getDogWalkerImpl().getDogWalkerByUniqueId(uniqueId)).build();
    }

    @GET
    @Path("/allDogWalkers")
    public Response getAllDogWalkers() {
        return Response.ok(getDogWalkerImpl().getAllDogWalkers()).build();
    }

    @PUT
    @Path("/allUpdateDogWalker")
    public Response allUpdateDogWalker(DogWalker dogWalker) {
        return Response.ok(getDogWalkerImpl().allUpdateDogWalker(dogWalker)).build();
    }

    @PUT
    @Path("/updateDogWalker")
    public Response updateDogWalker(DogWalker dogWalker) {
        return Response.ok(getDogWalkerImpl().updateDogWalker(dogWalker)).build();
    }

    @DELETE
    @Path("/deleteDogWalker")
    public Response deleteDogWalkerByUniqueId(@QueryParam("uniqueId") String uniqueId) {
        return Response.ok(getDogWalkerImpl().deleteDogWalker(uniqueId)).build();
    }

    /////////////////////////////////////////////////////////////
    //Rest for request walk
    private RequestWalkImpl requestWalkImpl;

    public RequestWalkImpl getRequestWalkImpl() {
        if (requestWalkImpl == null) {
            requestWalkImpl = RequestWalkImpl.create(ao);
        }
        return requestWalkImpl;
    }

    @POST
    @Path("/createRequestWalk")
    public Response createRequestWalk(RequestWalk requestWalk) {
        return Response.ok(getRequestWalkImpl().createRequestWalk(requestWalk)).build();
    }

    @GET
    @Path("/allRequestWalks")
    public Response getAllRequestWalks() {
        return Response.ok(getRequestWalkImpl().getAllRequestWalks()).build();
    }

    @GET
    @Path("/requestWalk")
    public Response getRequestWalkByUniqueId(@QueryParam("uniqueId") String uniqueId) {
        return Response.ok(getRequestWalkImpl().getRequestWalkByUniqueId(uniqueId)).build();
    }

    @PUT
    @Path("/updateRequestWalk")
    public Response updateRequestWalk(RequestWalk requestWalk) {
        return Response.ok(getRequestWalkImpl().updateRequestWalk(requestWalk)).build();
    }

    @DELETE
    @Path("/deleteRequestWalk")
    public Response deleteRequestWalkByUniqueId(@QueryParam("uniqueId") String uniqueId) {
        return Response.ok(getRequestWalkImpl().deleteRequestWalk(uniqueId)).build();
    }

    /////////////////////////////////////////////////////////////
    //Rest for History Requests

    private HistoryImpl historyImpl;

    public HistoryImpl getHistoryImpl() {
        if (historyImpl == null) {
            historyImpl = HistoryImpl.create(ao);
        }
        return historyImpl;
    }

    @GET
    @Path("/getHistoryRequest")
    public  Response getHistoryRequest(@QueryParam("owner") String owner,
                                       @QueryParam("dog") String dog,
                                       @QueryParam("walker") String walker) {
        return Response.ok(getHistoryImpl().getHistoryWalks(owner, dog, walker)).build();
    }

}
