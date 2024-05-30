package com.example.buensaborback.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
@Audited
public class Sucursal extends Base{

    private String nombre;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;
    private Boolean casaMatriz;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domicilio_id")
    private Domicilio domicilio;
    
    @ManyToMany(mappedBy = "sucursales", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    @Builder.Default
    @ToString.Exclude
    @JsonBackReference(value = "sucursal_categorias")
    private Set<Categoria> categorias = new HashSet<>();

    @OneToMany(mappedBy = "sucursal", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    @JsonBackReference(value = "sucursal_pedidos")
    private Set<Pedido> pedidos = new HashSet<>();

    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    @JsonBackReference(value = "sucursal_stocks_insumos")
    private Set<StockInsumo> stocksInsumos = new HashSet<>();

    @ManyToMany(mappedBy = "sucursales")
    @ToString.Exclude
    @JsonIgnoreProperties({"denominacion", "fechaDesde", "fechaHasta", "sucursales"})
    @Builder.Default
    private Set<Promocion> promociones = new HashSet<>();

    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    @JsonBackReference(value = "sucursal_empleados")
    private Set<Empleado> empleados = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    @ToString.Exclude
    @JsonIgnoreProperties("sucursales")
    private Empresa empresa;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "imagen_id")
    @NotAudited
    protected ImagenSucursal imagen;
}
