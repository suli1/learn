package com.suli.learnning.factory.processor;

import com.suli.learnning.factory.annotations.Factory;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by suli690 on 2017-01-01.
 */
//@AutoService(Factory.class)
public class FactoryProcessor extends AbstractProcessor {

    private Types typesUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager; // 提供给注解处理器一个报告错误，警告以及提示信息的途径
    private Map<String, FactoryGroupedClasses> factoryClasses = new LinkedHashMap<>();


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typesUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(Factory.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 遍历所有被注解(@Factory)了的元素
        for (Element element : roundEnv.getElementsAnnotatedWith(Factory.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                error(element, "Only classes can be annotated with @%s", Factory.class.getSimpleName());
                return true;
            }

            TypeElement typeElement = (TypeElement) element;

            try {
                FactoryAnnotatedClass annotatedClass = new FactoryAnnotatedClass(typeElement);

                if (!isValidClass(annotatedClass)) {
                    return true;
                }

                FactoryGroupedClasses factoryClass = factoryClasses.get(annotatedClass.getQualifiedFactoryGroupName());
                if (factoryClass == null) {
                    String qualifiedClassName = annotatedClass.getQualifiedFactoryGroupName();
                    factoryClass = new FactoryGroupedClasses(qualifiedClassName);
                    factoryClasses.put(qualifiedClassName, factoryClass);
                }

                factoryClass.add(annotatedClass);

            } catch (IllegalArgumentException e) {
                error(typeElement, e.getMessage());
                return true;
            } catch (IdAlreadyUsedException e) {
                // 如果和其他的@Factory标注的类的id相同冲突，
                // 抛出IdAlreadyUsedException异常
                FactoryAnnotatedClass existing = e.getExisting();
                error(typeElement,
                        "Conflict: The class %s is annotated with @%s with id ='%s' but %s already uses the same id",
                        typeElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                        existing.getTypeElement().getQualifiedName().toString());
                return true;
            }

        }

        // 已经收集了所有的被@Factory注解的类保存到为FactoryAnnotatedClass，现在生成代码
        try {
            for (FactoryGroupedClasses factoryClass : factoryClasses.values()) {
                factoryClass.generateCode(elementUtils, filer);
            }

            factoryClasses.clear();
        } catch (IOException e) {
            error(null, e.getMessage());
        }

        return true;
    }

    private boolean isValidClass(FactoryAnnotatedClass item) {
        TypeElement classElement = item.getTypeElement();

        // 检查是否是公开类
        if (!classElement.getModifiers().contains(Modifier.PUBLIC)) {
            error(classElement, "The class %s is not public.",
                    classElement.getQualifiedName().toString());
            return false;
        }

        // 检查必须是非抽象类
        if (classElement.getModifiers().contains(Modifier.ABSTRACT)) {
            error(classElement, "The class %s is abstract. You can't annotate abstract class with @%s",
                    classElement.getQualifiedName().toString(), Factory.class.getSimpleName());
            return false;
        }

        // 检查继承关系, 必须是@Factory.type()指定类型的子类
        TypeElement superClassElement = elementUtils.getTypeElement(item.getQualifiedFactoryGroupName());
        if (superClassElement.getKind() == ElementKind.INTERFACE) {
            if (!classElement.getInterfaces().contains(superClassElement.asType())) {
                error(classElement, "The class %s annotated with @%s must implement the interface %s",
                        classElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                        item.getQualifiedFactoryGroupName());
                return false;
            }
        } else {
            // 检查子类
            TypeElement currentClass = classElement;
            while (true) {
                TypeMirror superClassType = currentClass.getSuperclass();
                if (superClassType.getKind() == TypeKind.NONE) {
                    // 到达了基本类型(java.lang.Object), 所以退出
                    error(classElement, "The class %s annotated with @%s must inherit from %s",
                            classElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                            item.getQualifiedFactoryGroupName());
                    return false;
                }

                if (superClassElement.toString().equals(item.getQualifiedFactoryGroupName())) {
                    break;
                }

                // 继续搜索父类
                currentClass = (TypeElement) typesUtils.asElement(superClassType);
            }
        }

        // 检查是否提供了默认的公开构造函数
        for (Element enclosed : classElement.getEnclosedElements()) {
            if (enclosed.getKind() == ElementKind.CONSTRUCTOR) {
                ExecutableElement constructorElement = (ExecutableElement) enclosed;
                if (constructorElement.getParameters().size() == 0 && constructorElement.getModifiers().contains(Modifier.PUBLIC)) {
                    // 找到默认构造函数
                    return true;
                }
            }
        }

        // 没有找到默认构造函数
        error(classElement, "The class %s must provide an public empty default constructor",
                classElement.getQualifiedName().toString());

        return false;
    }


    private void error(Element e, String msg, Object... args) {
        if (messager != null) {
            messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
        }
    }

}
