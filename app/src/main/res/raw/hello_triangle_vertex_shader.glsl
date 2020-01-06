#version 300 es
precision highp float;
layout(location = 0) in vec3 aVertex;

void main(){
    vec4 positon = vec4(aVertex, 1.0);
    gl_Position = positon;
}