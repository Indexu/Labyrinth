
#ifdef GL_ES
precision mediump float;
#endif

uniform float u_shininessFactor;
uniform float u_globalAmbience;

uniform vec4 u_lightDiffuse;
uniform vec4 u_lightSpecular;
uniform vec4 u_lightAmbience;

uniform vec4 u_materialDiffuse;
uniform vec4 u_materialSpecular;
uniform vec4 u_materialAmbience;
uniform vec4 u_materialEmission;

varying vec4 v_n;
varying vec4 v_s;
varying vec4 v_h;

void main()
{
    // Lighting

	float lampert = dot(v_n, v_s) / (length(v_n) * length(v_s));
	lampert = ((lampert < 0.0) ? 0.0 : lampert);

	float phong = dot(v_n, v_h) / (length(v_n) * length(v_h));
    phong = (phong < 0.0 ? 0.0 : phong);
    phong = pow(phong, u_shininessFactor);

    vec4 globalAmbience = u_globalAmbience * u_materialAmbience;
    vec4 ambience = u_lightAmbience * u_materialAmbience;
    vec4 diffuse = lampert * u_lightDiffuse * u_materialDiffuse;
    vec4 specular = phong * u_lightSpecular * u_materialSpecular;

	gl_FragColor = globalAmbience + ambience + diffuse + specular + u_materialEmission;
}