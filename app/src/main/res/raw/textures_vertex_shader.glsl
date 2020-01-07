#version 300 es

layout(location = 0) in vec3 aVertex;
layout(location = 1) in vec2 aTexCoord;

out vec2 texCoord;

void main(){
    gl_Position = vec4(aVertex, 1.0);
    texCoord = aTexCoord;
}