package com.example.miaow.plugin.bury

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class BuryPointClassVisitor extends ClassVisitor {

    BuryPointClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor)
    }

    /**
     * @param version 类版本
     * @param access 修饰符
     * @param name 类名
     * @param signature 泛型信息
     * @param superName 父类
     * @param interfaces 实现的接口
     */
    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
    }

    @Override
    void visitEnd() {
        super.visitEnd()
    }

    /**
     * 扫描类的方法进行调用
     * @param access 修饰符
     * @param name 方法名字
     * @param descriptor 方法签名
     * @param signature 泛型信息
     * @param exceptions 抛出的异常
     * @return
     */
    @Override
    MethodVisitor visitMethod(int methodAccess, String methodName, String methodDescriptor, String signature, String[] exceptions) {
        def methodVisitor = super.visitMethod(methodAccess, methodName, methodDescriptor, signature, exceptions)
        if ((methodAccess & Opcodes.ACC_INTERFACE) == 0 && "<init>" != methodName && "<clinit>" != methodName) {
            methodVisitor = new BuryPointAdviceAdapter(api, methodVisitor, methodAccess, methodName, methodDescriptor)
        }
        return methodVisitor
    }

}

