package com.suli.learnning.factory.processor;

import com.suli.learnning.factory.annotations.Factory;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

/**
 * Created by suli690 on 2017-01-01.
 */
public class FactoryAnnotatedClass {
    private String id;
    private TypeElement annotatedClassElement;
    private String qualifiedSuperClassName;
    private String simpleTypeName;

    public FactoryAnnotatedClass(TypeElement annotatedClassElement) {
        this.annotatedClassElement = annotatedClassElement;
        Factory annotation = annotatedClassElement.getAnnotation(Factory.class);
        id = annotation.id();

        if (id.isEmpty()) {
            throw new IllegalArgumentException(String.format("id() in @%s for class %s is null or empty! that's not allowed",
                    Factory.class.getSimpleName(), annotatedClassElement.getQualifiedName().toString()));
        }

        // Get the full QualifiedTypeName
        try {
            // 如果这个类已经被编译，即第三方.jar包含已编译的被@Factory注解的.class文件, 可直接获取
            Class<?> clazz = annotation.type();
            qualifiedSuperClassName = clazz.getCanonicalName();
            simpleTypeName = clazz.getSimpleName();
        } catch (MirroredTypeException mte) {
            // 如果这个类还没有被编译，会抛出该异常，在TypeMirror中强转类型获取
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            qualifiedSuperClassName = classTypeElement.getQualifiedName().toString();
            simpleTypeName = classTypeElement.getSimpleName().toString();
        }
    }

    /**
     * 获取在{@link Factory#id()}中指定的id
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * 获取在{@link Factory#type()}中指定的类型合法全名
     *
     * @return the qualifiedSuperClassName
     */
    public String getQualifiedFactoryGroupName() {
        return qualifiedSuperClassName;
    }

    /**
     * 获取在{@link Factory#type()}中指定的类型的简单名字
     *
     * @return the simpleTypeName
     */
    public String getSimpleFactoryGroupTypeName() {
        return simpleTypeName;
    }

    /**
     * 获取被{@link Factory}注解的原始数据
     *
     * @return the annotatedClassElement
     */
    public TypeElement getTypeElement() {
        return annotatedClassElement;
    }


}
