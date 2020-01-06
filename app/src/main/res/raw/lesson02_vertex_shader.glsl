#version 300 es
precision highp float;
in vec2 aPosition;

void main(){
    vec4 positon = vec4(aPosition, 0.0, 1.0);
    gl_Position = positon;
}