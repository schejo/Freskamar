/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

/**
 *
 * @author kevin
 */
public class ClientesMd {

    private String usuario;
    private String CodigoCliente;
    private String NombreComercial;
    private String Nit;
    private String Direccion;

    private String Telefono;
    private String FechaAlta;
    private String usuarioAlta;
    private String correCleinte;
    private String ObtenerFecha;

    private String resp;
    private String msg;
    private String dir;

    public String getObtenerFecha() {
        return ObtenerFecha;
    }

    public void setObtenerFecha(String ObtenerFecha) {
        this.ObtenerFecha = ObtenerFecha;
    }
    
    

    public String getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(String usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreCleinte() {
        return correCleinte;
    }

    public void setCorreCleinte(String correCleinte) {
        this.correCleinte = correCleinte;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getFechaAlta() {
        return FechaAlta;
    }

    public void setFechaAlta(String FechaAlta) {
        this.FechaAlta = FechaAlta;
    }

    public ClientesMd() {
    }

    public String getCodigoCliente() {
        return CodigoCliente;
    }

    public void setCodigoCliente(String CodigoCliente) {
        this.CodigoCliente = CodigoCliente;
    }

    public String getNombreComercial() {
        return NombreComercial;
    }

    public void setNombreComercial(String NombreComercial) {
        this.NombreComercial = NombreComercial;
    }

    public String getNit() {
        return Nit;
    }

    public void setNit(String Nit) {
        this.Nit = Nit;
    }

}
