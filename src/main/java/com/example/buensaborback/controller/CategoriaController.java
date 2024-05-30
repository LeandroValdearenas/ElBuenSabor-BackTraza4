package com.example.buensaborback.controller;

import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import com.example.buensaborback.domain.entities.Categoria;
import com.example.buensaborback.service.CategoriaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/categorias")
public class CategoriaController extends BaseControllerImpl<Categoria, CategoriaServiceImpl>{

    private final CategoriaServiceImpl service;
    public CategoriaController(CategoriaServiceImpl service) {
        super(service);
        this.service = service;
    }

    @GetMapping("sucursal/{sucursalId}")
    public ResponseEntity<?> buscarXSucursal(@PathVariable Long sucursalId) {
        try {
            List<Categoria> categorias = service.buscarXSucursal(sucursalId);
            return ResponseEntity.status(HttpStatus.OK).body(categorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Categoria categoria) {
        try {
            categoria.getSubCategorias().forEach(subcategoria -> subcategoria.setCategoriaPadre(categoria));
            categoria.getSucursales().forEach(sucursal -> sucursal.getCategorias().add(categoria));
            return ResponseEntity.status(HttpStatus.OK).body(service.save(categoria));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @Override
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody Categoria categoria){
        try {
            categoria.getSubCategorias().forEach(subcategoria -> subcategoria.setCategoriaPadre(categoria));
            categoria.getSucursales().forEach(sucursal -> sucursal.getCategorias().add(categoria));
            categoria.getSucursales().forEach(s -> System.out.println(s.getNombre()));
            Categoria searchedEntity = service.findById(id);
            if (searchedEntity == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontro la entidad\"}");
            }
            categoria.setCategoriaPadre(searchedEntity.getCategoriaPadre());
            return ResponseEntity.status(HttpStatus.OK).body(service.update(categoria));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }
}

