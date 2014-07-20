#version 410
layout (location = 0) in vec4 vertexpos;
out vec4 gl_Position;

void main()
{
	gl_Position = vertexpos;
}