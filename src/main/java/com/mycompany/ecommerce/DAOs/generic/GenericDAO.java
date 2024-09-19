package com.mycompany.ecommerce.DAOs.generic;

import java.util.List;

public interface GenericDAO<T>{

    public void inserir(Object... params) throws Exception;
    public T buscarPorId(Object id) throws Exception;
    public List<T> buscarTodos() throws Exception;
    public void atualizar(T t,  Object id) throws Exception;
    public void delete(Long id) throws Exception;


}
