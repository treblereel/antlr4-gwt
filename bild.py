import sys
import os
sys.path.append(os.path.abspath("/Users/parrt/github/bild"))

from bilder import *

VERSION = "4.4"

def parsers():
    antlr3("tool/src/org/antlr/v4/parse", "gen", package="org.antlr.v4.parse")
    antlr3("tool/src/org/antlr/v4/codegen", "gen", package="org.antlr.v4.codegen", args=["-lib","tool/src/org/antlr/v4/parse"])
    antlr4("runtime/Java/src/org/antlr/v4/runtime/tree/xpath", "gen", package="org.antlr.v4.runtime.tree.xpath")

def compile():
    require(parsers)
    cp = uniformpath("out")+os.pathsep+\
        os.path.join(JARCACHE,"antlr-3.5.1-complete.jar")+os.pathsep+\
        "runtime/Java/lib/org.abego.treelayout.core.jar"
    javac("runtime/JavaAnnotations/src/", "out", cp=cp)
    javac("runtime/Java/src", "out", cp=cp)
    javac("tool/src", "out", cp=cp)
    javac("gen", "out", cp=cp)

def mkjar():
    require(compile)
    copytree(src="tool/resources", dst="out")
    manifest = """Version: %s
Main-Class: org.antlr.v4.Tool
""" % VERSION
    # copy 3.5.1 in there
    unjar("runtime/Java/lib/org.abego.treelayout.core.jar", trgdir="out")
    jar("dist/antlr-"+VERSION+"-complete.jar", srcdir="out", manifest=manifest)

def all():
    mkjar()

def clean():
    rmdir("out")
    rmdir("gen")

mkjar()
