package ar.edu.ubp.das.restaurante2soap.services;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import org.springframework.stereotype.Service;

@Service
@WebService
public class PerukaiWS {

    @WebMethod
    public String ping() {
        return "Perukai OK";
    }
}

