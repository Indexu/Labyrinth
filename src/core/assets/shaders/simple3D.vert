#ifdef GL_ES
precision mediump float;
#endif

attribute vec3 a_position;
attribute vec3 a_normal;

uniform mat4 u_modelMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_projectionMatrix;

uniform vec4 u_eyePosition;
uniform float u_shininessFactor;
uniform float u_globalAmbience;

uniform vec4 u_lightPosition;
uniform vec4 u_lightDiffuse;
uniform vec4 u_lightSpecular;
uniform vec4 u_lightAmbience;

uniform vec4 u_materialDiffuse;
uniform vec4 u_materialSpecular;
uniform vec4 u_materialAmbience;
uniform vec4 u_materialEmission;

varying vec4 v_color;

void main()
{
	vec4 position = vec4(a_position.x, a_position.y, a_position.z, 1.0);
	position = u_modelMatrix * position;

	vec4 normal = vec4(a_normal.x, a_normal.y, a_normal.z, 0.0);
	normal = u_modelMatrix * normal;

	// global coordinates

	// Lighting

	vec4 s = u_lightPosition - position;

	float lampert = dot(normal, s) / (length(normal) * length(s));
	lampert = ((lampert < 0.0) ? 0.0 : lampert);

	vec4 v = u_eyePosition - position;
	vec4 h = s + v;

	float phong = dot(normal, h) / (length(normal) * length(h));
    phong = (phong < 0.0 ? 0.0 : phong);
    phong = pow(phong, u_shininessFactor);

    vec4 globalAmbience = u_globalAmbience * u_materialAmbience;
    vec4 ambience = u_lightAmbience * u_materialAmbience;
    vec4 diffuse = lampert * u_lightDiffuse * u_materialDiffuse;
    vec4 specular = phong * u_lightSpecular * u_materialSpecular;

	v_color = globalAmbience + ambience + diffuse + specular + u_materialEmission;

	position = u_viewMatrix * position;
	// normal = u_viewMatrix * normal;

	// eye coordinates

	// v_color = (dot(normal, vec4(0,0,1,0)) / length(normal)) * u_color;
	// v_color = (dot(normal, normalize(vec4(-position.x,-position.y,-position.z,0))) / length(normal)) * u_color;

	gl_Position = u_projectionMatrix * position;
}