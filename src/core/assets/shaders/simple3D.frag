
#ifdef GL_ES
precision mediump float;
#endif

struct spotLight
{
    vec4 color;
    vec4 direction;
    float spotFactor;
    float constantAttenuation;
    float linearAttenuation;
    float quadraticAttenuation;
};

uniform float u_shininessFactor;
uniform float u_spotFactor;

uniform vec4 u_globalAmbience;

uniform spotLight u_spotLight1;

uniform vec4 u_lightColor;
uniform vec4 u_lightDirection;

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

    vec4 color;

    float len_s = length(v_s);

	float lampert = dot(v_n, v_s) / (length(v_n) * len_s);
	lampert = ((lampert < 0.0) ? 0.0 : lampert);

	float phong = dot(v_n, v_h) / (length(v_n) * length(v_h));
    phong = (phong < 0.0 ? 0.0 : pow(phong, u_shininessFactor));

    float spotAttenuation = dot(-v_s, u_spotLight1.direction) / (len_s * length(u_spotLight1.direction));
    spotAttenuation = (spotAttenuation < 0.0 ? 0.0 : pow(spotAttenuation, u_spotLight1.spotFactor));
    float distanceAttenuation = 1.0 / (u_spotLight1.constantAttenuation + len_s * u_spotLight1.linearAttenuation + len_s * len_s * u_spotLight1.quadraticAttenuation);

    color += u_spotLight1.color * u_materialDiffuse * lampert;
    color += u_spotLight1.color * u_materialSpecular * phong;

    color *= distanceAttenuation * spotAttenuation;

    color += u_globalAmbience * u_materialAmbience;
    color += u_materialEmission;

    color.a = u_materialTransparency;

	gl_FragColor = color;
}