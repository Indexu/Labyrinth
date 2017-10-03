
#ifdef GL_ES
precision mediump float;
#endif

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

varying vec4 v_position;
varying vec4 v_normal;

void main()
{
// Lighting

	vec4 s = u_lightPosition - v_position;

	float lampert = dot(v_normal, s) / (length(v_normal) * length(s));
	lampert = ((lampert < 0.0) ? 0.0 : lampert);

	vec4 v = u_eyePosition - v_position;
	vec4 h = s + v;

	float phong = dot(v_normal, h) / (length(v_normal) * length(h));
    phong = (phong < 0.0 ? 0.0 : phong);
    phong = pow(phong, u_shininessFactor);

    vec4 globalAmbience = u_globalAmbience * u_materialAmbience;
    vec4 ambience = u_lightAmbience * u_materialAmbience;
    vec4 diffuse = lampert * u_lightDiffuse * u_materialDiffuse;
    vec4 specular = phong * u_lightSpecular * u_materialSpecular;

	gl_FragColor = globalAmbience + ambience + diffuse + specular + u_materialEmission;
}