package edu.upc.dsa.services;

import edu.upc.dsa.UserManager;
import edu.upc.dsa.UserManagerImpl;
import edu.upc.dsa.models.Object;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/game", description = "Endpoint to User Service")
@Path("/game")
public class UserService {
    private UserManager manager;

    public UserService(){
        this.manager= UserManagerImpl.getInstance();
        if(manager.userListsize()==0){
            this.manager.addUser("Joel","Pasword");
            this.manager.addUser("Maria","CaraDura");
            this.manager.addUser("Esther","Estresada");
        }
        if(manager.objectListsize()==0){
            this.manager.addObject("Ulleres de visió nocturna","Ulleres que et permeten veure quan no hi ha llum");
            this.manager.addObject("Ganzua","Eïna que et permet obrir panys");
            this.manager.addObject("Botes","Botes supersilencioses");
        }
    }

    //Add User
    @POST
    @ApiOperation(value = "Create a new User", notes = "Name and Password")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= User.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(User u) {

        if (u.getName()==null || u.getPsw()==null)  return Response.status(500).entity(u).build();
        this.manager.addUser(u);
        return Response.status(201).entity(u).build();
    }

    //Add Objecte
    @POST
    @ApiOperation(value = "Create a new Object", notes = "Name and Description")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= Object.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newObject(Object o) {
        if (o.getName()==null || o.getDescription()==null){
            return Response.status(500).entity(o).build();
        }
        this.manager.addObject(o);
        return Response.status(201).entity(o).build();
    }

    //Update User
    @PUT
    @ApiOperation(value = "Update a User", notes = "Update a User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/")
    public Response updateUser(User u) {
        User user = this.manager.updateUser(u);
        if (user == null){
            return Response.status(404).build();
        }else{
            return Response.status(201).build();
        }
    }

    //Get User
    @GET
    @ApiOperation(value = "Get a User", notes = "Get a User by Name")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrack(@PathParam("name") String name) {
        User user = this.manager.getUser(name);
        if (user == null){
            return Response.status(404).build();
        }else{
            return Response.status(201).entity(user).build();
        }
    }

    //Get All Users
    @GET
    @ApiOperation(value = "Get All Users", notes = "Get All Users")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {

        List<User> users = this.manager.getAllUsers();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.status(201).entity(entity).build()  ;

    }

    //Get All Objects
    @GET
    @ApiOperation(value = "Get All Object", notes = "Get All Object")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Object.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObject() {

        List<Object> objects = this.manager.getAllObjects();
        GenericEntity<List<Object>> entity = new GenericEntity<List<Object>>(objects) {};
        return Response.status(201).entity(entity).build()  ;

    }

    //Delete User
    @DELETE
    @ApiOperation(value = "Delete an User", notes = "Delete an User By Name")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{name}")
    public Response deleteUser(@PathParam("name") String name) {
        User user = this.manager.getUser(name);
        if (user == null){
            return Response.status(404).build();
        }
        else{
            this.manager.deleteUser(name);
            return Response.status(201).build();
        }
    }

    //Delete Object
    @DELETE
    @ApiOperation(value = "Delete an Object", notes = "Delete an Object By Name")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Object not found")
    })
    @Path("/{name}")
    public Response deleteObject(@PathParam("name") String name) {
        Object object = this.manager.getObject(name);
        if (object == null){
            return Response.status(404).build();
        }
        else{
            this.manager.deleteUser(name);
            return Response.status(201).build();
        }
    }
}
