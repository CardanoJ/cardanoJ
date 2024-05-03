package com.cardanoj.rust.processor;

import com.cardanoj.rust.annotation.processor.model.ClassDefinition;
import com.squareup.javapoet.TypeSpec;

/**
 * Interface for code generator
 */
public interface CodeGenerator {
    TypeSpec generate(ClassDefinition classDefinition);
}
