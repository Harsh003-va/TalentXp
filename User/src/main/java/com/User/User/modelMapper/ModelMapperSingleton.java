package com.User.User.modelMapper;

import org.modelmapper.ModelMapper;

public class ModelMapperSingleton {
    private static ModelMapper mapper;

    private ModelMapperSingleton(){}
    public static ModelMapper getInstance(){
        if(mapper==null){

            mapper=new ModelMapper();
        }

        return mapper;
    }
}
