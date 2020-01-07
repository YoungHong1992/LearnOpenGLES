#version 300 es
precision highp float;

in vec2 texCoord;
layout(location = 0) uniform sampler2D brickTexture;
layout(location = 1) uniform sampler2D smileTexture;

out vec4 finalColor;

void main(){
    vec4 brickColor = texture(brickTexture, texCoord);
    vec4 smileColor = texture(smileTexture, texCoord);
    finalColor = brickColor * 0.7f + smileColor*0.3f;
}