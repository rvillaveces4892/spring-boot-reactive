package com.example.reactive.springbootreactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactiveApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringBootReactiveApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootReactiveApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Flux<Usuario> nombres = Flux.just("wilson guzman", "ramiro villaveces", "natalia amaranto", "carlos alvarez","william cases", "bruce willis", "bruce lee")
                .map(nombre -> new Usuario(nombre.split(" ")[0],nombre.split(" ")[1]))
                .doOnNext(usuario -> {
                        if(usuario == null){
                           throw new RuntimeException("Nombres no pueden ser vacios");
                        }
                            System.out.println(usuario.getNombre());
                })
                .map(usuario -> {
                    String nombre = usuario.getNombre().toUpperCase();
                    usuario.setNombre(nombre);
                    return usuario;
                });
        nombres.subscribe(e -> log.info(e.getNombre()+ " - " +e.getApellido()),
                error -> log.error(error.getMessage()),
                new Runnable() {
                    @Override
                    public void run() {
                        log.info("ha finalizado !!");
                    }
                });

    }
}
