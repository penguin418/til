cmake_minimum_required(VERSION 3.5)
project(hw9 C)

set(CMAKE_C_STANDARD 99)
set(CMAKE_C_COMPILER "/usr/bin/cc")
ADD_COMPILE_OPTIONS( -g -Wall )

include_directories(${CMAKE_CURRENT_SOURCE_DIR})

add_executable(interp y.tab.c lex.yy.c interp.c lib.c)
INCLUDE_DIRECTORIES(${CMAKE_CURRENT_SOURCE_DIR})
ADD_CUSTOM_COMMAND(
        OUTPUT lex.yy.c
        PRE_BUILD
        COMMAND flex  ${CMAKE_CURRENT_SOURCE_DIR}/interp.l)
ADD_CUSTOM_COMMAND(
        OUTPUT y.tab.c
        PRE_BUILD
        COMMAND yacc -d  ${CMAKE_CURRENT_SOURCE_DIR}/interp.y)