
#ifdef GL_ES
precision mediump float;
#endif

uniform float u_shininessFactor;
uniform float u_globalAmbience;

uniform vec4 u_lightColor;

uniform vec4 u_materialDiffuse;
uniform vec4 u_materialSpecular;
uniform vec4 u_materialAmbience;
uniform vec4 u_materialEmission;
uniform float u_materialTransparency;

varying vec4 v_n;
varying vec4 v_s;
varying vec4 v_h;

void main()
{
    // Lighting

    vec4 color = u_materialEmission;

	float lampert = dot(v_n, v_s) / (length(v_n) * length(v_s));
	lampert = ((lampert < 0.0) ? 0.0 : lampert);

	float phong = dot(v_n, v_h) / (length(v_n) * length(v_h));
    phong = (phong < 0.0 ? 0.0 : phong);

    color += u_lightColor * u_materialDiffuse * lampert;
    color += u_lightColor * u_materialSpecular * pow(phong, u_shininessFactor);

    color += u_globalAmbience * u_materialAmbience;

    color.a = u_materialTransparency;

	gl_FragColor = color;
}