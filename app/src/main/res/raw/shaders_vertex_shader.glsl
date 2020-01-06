#version 300 es
precision highp float;
layout(location = 0) in vec2 aVertex;
layout(location = 1) in vec3 aColor;
out vec4 finalColor;

void main(){
    vec4 positon = vec4(aVertex, 0.0, 1.0);
    gl_Position = positon;
    finalColor = vec4(aColor, 1.0);
}