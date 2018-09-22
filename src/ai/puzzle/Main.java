package ai.puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Main {

	public enum Colores { YELLOW, ORANGE, BLUE, RED, GREEN, WHITE, DEFAULT }

	public enum Reglas 	{ 	FRONT_CLOCKWISE, FRONT_INVERTED,
							RIGHT_CLOCKWISE, RIGHT_INVERTED,
							UP_CLOCKWISE, UP_INVERTED,
							BOTTOM_CLOCKWISE, BOTTOM_INVERTED,
							LEFT_CLOCKWISE, LEFT_INVERTED,
							DOWN_CLOCKWISE, DOWN_INVERTED
						}

	public static Character[][][] inicializarCubo() {
		Character[][][] nuevoEstado = {
					{
						{ 'W', 'W', 'W' },
						{ 'W', 'W', 'W' },
						{ 'W', 'W', 'W' }
					},
		{
			{ 'O', 'O', 'O' },
			{ 'O', 'O', 'O' },
			{ 'O', 'O', 'O' }
		},
					{
						{ 'G', 'G', 'G' },
						{ 'G', 'G', 'G' },
						{ 'G', 'G', 'G' }
					},
											{
												{ 'R', 'R', 'R' },
												{ 'R', 'R', 'R' },
												{ 'R', 'R', 'R' }
											},
																	{
																		{ 'B', 'B', 'B' },
																		{ 'B', 'B', 'B' },
																		{ 'B', 'B', 'B' }
																	},
					{
						{ 'Y', 'Y', 'Y' },
						{ 'Y', 'Y', 'Y' },
						{ 'Y', 'Y', 'Y' }
					}
		};

		return nuevoEstado;
	}

	public static void textcolor(Colores color) {
		switch (color) {
			case YELLOW:
				System.out.print("\033[0;33m");
				break;
			case ORANGE:
				System.out.print("\033[0;35m");
				break;
			case BLUE:
				System.out.print("\033[0;34m");
				break;
			case RED:
				System.out.print("\033[0;31m");
				break;
			case GREEN:
				System.out.print("\033[0;32m");
				break;
			case WHITE:
				System.out.print("\033[0;37m");
				break;
			case DEFAULT:
				System.out.print("\033[0;37m");
				break;
			default:
				break;
		}
	}

	public static void mostrarPieza(char pieza) {
		switch (pieza) {
			case 'Y':
				textcolor(Colores.YELLOW);
				break;
			case 'O':
				textcolor(Colores.ORANGE);
				break;
			case 'B':
				textcolor(Colores.BLUE);
				break;
			case 'R':
				textcolor(Colores.RED);
				break;
			case 'G':
				textcolor(Colores.GREEN);
				break;
			case 'W':
				textcolor(Colores.WHITE);
				break;
			default:
				textcolor(Colores.DEFAULT);
				break;
			}

			System.out.print(pieza + " ");
	}

	public static void mostrarEstado(Character[][][] estado) {
		for (int i = 0; i < 3; i++)
		{
			System.out.print("         ");
			mostrarPieza(estado[0][i][0]);
			mostrarPieza(estado[0][i][1]);
			mostrarPieza(estado[0][i][2]);
			System.out.println();
		}

		System.out.println();

		for (int i = 0; i < 3; i++)
		{
			System.out.print("  ");
			mostrarPieza(estado[1][i][0]);
			mostrarPieza(estado[1][i][1]);
			mostrarPieza(estado[1][i][2]);
			System.out.print(" ");
			mostrarPieza(estado[2][i][0]);
			mostrarPieza(estado[2][i][1]);
			mostrarPieza(estado[2][i][2]);
			System.out.print(" ");
			mostrarPieza(estado[3][i][0]);
			mostrarPieza(estado[3][i][1]);
			mostrarPieza(estado[3][i][2]);
			System.out.print(" ");
			mostrarPieza(estado[4][i][0]);
			mostrarPieza(estado[4][i][1]);
			mostrarPieza(estado[4][i][2]);
			System.out.println();
		}

		System.out.println();

		for (int i = 0; i < 3; i++)
		{
			System.out.print("         ");
			mostrarPieza(estado[5][i][0]);
			mostrarPieza(estado[5][i][1]);
			mostrarPieza(estado[5][i][2]);
			System.out.println();
		}

		System.out.println("\n");

		textcolor(Colores.DEFAULT);
	}

	public static String obtenerClaveDeEstado(Character[][][] estado) {
		String clave = "";

		for (int k = 0; k < 6; ++k) {
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					clave += estado[k][i][j];
				}
			}
		}

		return clave;
	}

	public static boolean Cond_Term(Character[][][] Est_Act) {
		for (int k = 0; k < 6; ++k) {
			for (int i = 0; i < 3; ++i) {
				Character color = Est_Act[k][i][0];
				for (int j = 0; j < 3; ++j) {
					if (Est_Act[k][i][j] != color) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public static Character[][][] obtenerEstadoDeClave(String clave) {
		Character[][][] estado = new Character[6][3][3];
		int index = 0;

		for (int k = 0; k < 6; ++k) {
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					estado[k][i][j] = clave.charAt(index++);
				}
			}
		}

		return estado;
	}

	public static ArrayList<Character[][][]> ConstruirCamino(HashMap<String, Estado> AB, Estado Est_Act) {
		ArrayList<Character[][][]> cam = new ArrayList<>();

		String actual = obtenerClaveDeEstado(Est_Act.getEstado());

		while (actual != null) {
			cam.add(obtenerEstadoDeClave(actual));
			actual = AB.get(actual).getPadre();
		}

		return cam;
	}

	public static LinkedList<Reglas> Reglas_aplicables(Character[][][] Est_Act) {
		LinkedList<Reglas> RA = new LinkedList<>();

		for (Reglas regla: Reglas.values()) {
			RA.add(regla);
		}

		return RA;
	}

	public static void copy(Character[] v1, Character[] v2) {
		v1[0] = v2[0];
		v1[1] = v2[1];
		v1[2] = v2[2];
	}

	public static void copy2(Character[][] m1, int col, Character[] v2) {
		m1[0][col] = v2[0];
		m1[1][col] = v2[1];
		m1[2][col] = v2[2];
	}

	public static void front_clockwise(Character[][][] cube) {
		Character[] v1 = { cube[0][2][0], cube[0][2][1], cube[0][2][2] };
		Character[] v2 = { cube[3][2][0], cube[3][1][0], cube[3][0][0] };
		Character[] v3 = { cube[5][0][0], cube[5][0][1], cube[5][0][2] };
		Character[] v4 = { cube[1][2][2], cube[1][1][2], cube[1][0][2] };
	
		copy(cube[0][2], v4);
		copy2(cube[3], 0, v1);
		copy(cube[5][0], v2);
		copy2(cube[1], 2, v3);
	
		Character[] v5 = { cube[2][0][0], cube[2][0][1], cube[2][0][2] };
		Character[] v6 = { cube[2][2][2], cube[2][1][2], cube[2][0][2] };
		Character[] v7 = { cube[2][2][0], cube[2][2][1], cube[2][2][2] };
		Character[] v8 = { cube[2][2][0], cube[2][1][0], cube[2][0][0] };
	
		copy(cube[2][0], v8);
		copy2(cube[2], 2, v5);
		copy(cube[2][2], v6);
		copy2(cube[2], 0, v7);
	}

	public static void front_inverted(Character[][][] cube) {
		Character[] v1 = { cube[0][2][2], cube[0][2][1], cube[0][2][0] };
		Character[] v2 = { cube[3][0][0], cube[3][1][0], cube[3][2][0] };
		Character[] v3 = { cube[5][0][2], cube[5][0][1], cube[5][0][0] };
		Character[] v4 = { cube[1][0][2], cube[1][1][2], cube[1][2][2] };
	
		copy(cube[0][2], v2);
		copy2(cube[3], 0, v3);
		copy(cube[5][0], v4);
		copy2(cube[1], 2, v1);
	
		Character[] v5 = { cube[2][0][2], cube[2][0][1], cube[2][0][0] };
		Character[] v6 = { cube[2][2][2], cube[2][1][2], cube[2][0][2] };
		Character[] v7 = { cube[2][2][2], cube[2][2][1], cube[2][2][0] };
		Character[] v8 = { cube[2][0][0], cube[2][1][0], cube[2][2][0] };
	
		copy(cube[2][0], v6);
		copy2(cube[2], 2, v7);
		copy(cube[2][2], v8);
		copy2(cube[2], 0, v5);
	}

	public static void right_clockwise(Character[][][] cube) {
		Character[] v1 = { cube[0][2][2], cube[0][1][2], cube[0][0][2] };
		Character[] v2 = { cube[4][2][0], cube[4][1][0], cube[4][0][0] };
		Character[] v3 = { cube[5][0][2], cube[5][1][2], cube[5][2][2] };
		Character[] v4 = { cube[2][0][2], cube[2][1][2], cube[2][2][2] };
	
		copy2(cube[0], 2, v4);
		copy2(cube[4], 0, v1);
		copy2(cube[5], 2, v2);
		copy2(cube[2], 2, v3);
	
		Character[] v5 = { cube[3][0][0], cube[3][0][1], cube[3][0][2] };
		Character[] v6 = { cube[3][2][2], cube[3][1][2], cube[3][0][2] };
		Character[] v7 = { cube[3][2][0], cube[3][2][1], cube[3][2][2] };
		Character[] v8 = { cube[3][2][0], cube[3][1][0], cube[3][0][0] };
	
		copy(cube[3][0], v8);
		copy2(cube[3], 2, v5);
		copy(cube[3][2], v6);
		copy2(cube[3], 0, v7);
	}

	public static void right_inverted(Character[][][] cube) {
		Character[] v1 = { cube[0][0][2], cube[0][1][2], cube[0][2][2] };
		Character[] v2 = { cube[4][2][0], cube[4][1][0], cube[4][0][0] };
		Character[] v3 = { cube[5][2][2], cube[5][1][2], cube[5][0][2] };
		Character[] v4 = { cube[2][0][2], cube[2][1][2], cube[2][2][2] };
	
		copy2(cube[0], 2, v2);
		copy2(cube[4], 0, v3);
		copy2(cube[5], 2, v4);
		copy2(cube[2], 2, v1);
	
		Character[] v5 = { cube[3][0][2], cube[3][0][1], cube[3][0][0] };
		Character[] v6 = { cube[3][2][2], cube[3][1][2], cube[3][0][2] };
		Character[] v7 = { cube[3][2][2], cube[3][2][1], cube[3][2][0] };
		Character[] v8 = { cube[3][0][0], cube[3][1][0], cube[3][2][0] };
	
		copy(cube[3][0], v6);
		copy2(cube[3], 2, v7);
		copy(cube[3][2], v8);
		copy2(cube[3], 0, v5);
	}

	public static void up_clockwise(Character[][][] cube) {
		Character[] v1 = { cube[1][0][0], cube[1][0][1], cube[1][0][2] };
		Character[] v2 = { cube[2][0][0], cube[2][0][1], cube[2][0][2] };
		Character[] v3 = { cube[3][0][0], cube[3][0][1], cube[3][0][2] };
		Character[] v4 = { cube[4][0][0], cube[4][0][1], cube[4][0][2] };
	
		copy(cube[1][0], v2);
		copy(cube[2][0], v3);
		copy(cube[3][0], v4);
		copy(cube[4][0], v1);
	
		Character[] v5 = { cube[0][0][0], cube[0][0][1], cube[0][0][2] };
		Character[] v6 = { cube[0][2][2], cube[0][1][2], cube[0][0][2] };
		Character[] v7 = { cube[0][2][0], cube[0][2][1], cube[0][2][2] };
		Character[] v8 = { cube[0][2][0], cube[0][1][0], cube[0][0][0] };
	
		copy(cube[0][0], v8);
		copy2(cube[0], 2, v5);
		copy(cube[0][2], v6);
		copy2(cube[0], 0, v7);
	}
	
	public static void up_inverted(Character[][][] cube) {
		Character[] v1 = { cube[1][0][0], cube[1][0][1], cube[1][0][2] };
		Character[] v2 = { cube[2][0][0], cube[2][0][1], cube[2][0][2] };
		Character[] v3 = { cube[3][0][0], cube[3][0][1], cube[3][0][2] };
		Character[] v4 = { cube[4][0][0], cube[4][0][1], cube[4][0][2] };
	
		copy(cube[1][0], v4);
		copy(cube[2][0], v1);
		copy(cube[3][0], v2);
		copy(cube[4][0], v3);
	
		Character[] v5 = { cube[0][0][2], cube[0][0][1], cube[0][0][0] };
		Character[] v6 = { cube[0][2][2], cube[0][1][2], cube[0][0][2] };
		Character[] v7 = { cube[0][2][2], cube[0][2][1], cube[0][2][0] };
		Character[] v8 = { cube[0][0][0], cube[0][1][0], cube[0][2][0] };
	
		copy(cube[0][0], v6);
		copy2(cube[0], 2, v7);
		copy(cube[0][2], v8);
		copy2(cube[0], 0, v5);
	}

	public static void bottom_clockwise(Character[][][] cube) {
		Character[] v1 = { cube[0][0][2], cube[0][0][1], cube[0][0][0] };
		Character[] v2 = { cube[1][0][0], cube[1][1][0], cube[1][2][0] };
		Character[] v3 = { cube[3][0][2], cube[3][1][2], cube[3][2][2] };
		Character[] v4 = { cube[5][2][2], cube[5][2][1], cube[5][2][0] };
	
		copy2(cube[1], 0, v1);
		copy(cube[5][2], v2);
		copy2(cube[3], 2, v4);
		copy(cube[0][0], v3);
	
		Character[] v5 = { cube[4][0][0], cube[4][0][1], cube[4][0][2] };
		Character[] v6 = { cube[4][2][2], cube[4][1][2], cube[4][0][2] };
		Character[] v7 = { cube[4][2][0], cube[4][2][1], cube[4][2][2] };
		Character[] v8 = { cube[4][2][0], cube[4][1][0], cube[4][0][0] };
	
		copy(cube[4][0], v8);
		copy2(cube[4], 2, v5);
		copy(cube[4][2], v6);
		copy2(cube[4], 0, v7);
	}
	
	public static void bottom_inverted(Character[][][] cube) {
		Character[] v1 = { cube[0][0][0], cube[0][0][1], cube[0][0][2] };
		Character[] v2 = { cube[1][2][0], cube[1][1][0], cube[1][0][0] };
		Character[] v3 = { cube[3][2][2], cube[3][1][2], cube[3][0][2] };
		Character[] v4 = { cube[5][2][0], cube[5][2][1], cube[5][2][2] };
	
		copy2(cube[1], 0, v4);
		copy(cube[5][2], v3);
		copy2(cube[3], 2, v1);
		copy(cube[0][0], v2);
	
		Character[] v5 = { cube[4][0][2], cube[4][0][1], cube[4][0][0] };
		Character[] v6 = { cube[4][2][2], cube[4][1][2], cube[4][0][2] };
		Character[] v7 = { cube[4][2][2], cube[4][2][1], cube[4][2][0] };
		Character[] v8 = { cube[4][0][0], cube[4][1][0], cube[4][2][0] };
	
		copy(cube[4][0], v6);
		copy2(cube[4], 2, v7);
		copy(cube[4][2], v8);
		copy2(cube[4], 0, v5);
	}

	public static void left_clockwise(Character[][][] cube) {
		Character[] v1 = { cube[0][0][0], cube[0][1][0], cube[0][2][0] };
		Character[] v2 = { cube[2][0][0], cube[2][1][0], cube[2][2][0] };
		Character[] v3 = { cube[4][2][2], cube[4][1][2], cube[4][0][2] };
		Character[] v4 = { cube[5][2][0], cube[5][1][0], cube[5][0][0] };
	
		copy2(cube[0], 0, v3);
		copy2(cube[2], 0, v1);
		copy2(cube[5], 0, v2);
		copy2(cube[4], 2, v4);
	
		Character[] v5 = { cube[1][0][0], cube[1][0][1], cube[1][0][2] };
		Character[] v6 = { cube[1][2][2], cube[1][1][2], cube[1][0][2] };
		Character[] v7 = { cube[1][2][0], cube[1][2][1], cube[1][2][2] };
		Character[] v8 = { cube[1][2][0], cube[1][1][0], cube[1][0][0] };
	
		copy(cube[1][0], v8);
		copy2(cube[1], 2, v5);
		copy(cube[1][2], v6);
		copy2(cube[1], 0, v7);
	}
	
	public static void left_inverted(Character[][][] cube) {
		Character[] v1 = { cube[0][2][0], cube[0][1][0], cube[0][0][0] };
		Character[] v2 = { cube[2][0][0], cube[2][1][0], cube[2][2][0] };
		Character[] v3 = { cube[4][2][2], cube[4][1][2], cube[4][0][2] };
		Character[] v4 = { cube[5][0][0], cube[5][1][0], cube[5][2][0] };
	
		copy2(cube[0], 0, v2);
		copy2(cube[2], 0, v4);
		copy2(cube[5], 0, v3);
		copy2(cube[4], 2, v1);
	
		Character[] v5 = { cube[1][0][2], cube[1][0][1], cube[1][0][0] };
		Character[] v6 = { cube[1][2][2], cube[1][1][2], cube[1][0][2] };
		Character[] v7 = { cube[1][2][2], cube[1][2][1], cube[1][2][0] };
		Character[] v8 = { cube[1][0][0], cube[1][1][0], cube[1][2][0] };
	
		copy(cube[1][0], v6);
		copy2(cube[1], 2, v7);
		copy(cube[1][2], v8);
		copy2(cube[1], 0, v5);
	}

	public static void down_clockwise(Character[][][] cube) {
		Character[] v1 = { cube[1][2][0], cube[1][2][1], cube[1][2][2] };
		Character[] v2 = { cube[2][2][0], cube[2][2][1], cube[2][2][2] };
		Character[] v3 = { cube[3][2][0], cube[3][2][1], cube[3][2][2] };
		Character[] v4 = { cube[4][2][0], cube[4][2][1], cube[4][2][2] };
	
		copy(cube[1][2], v4);
		copy(cube[2][2], v1);
		copy(cube[3][2], v2);
		copy(cube[4][2], v3);
	
		Character[] v5 = { cube[5][0][0], cube[5][0][1], cube[5][0][2] };
		Character[] v6 = { cube[5][2][2], cube[5][1][2], cube[5][0][2] };
		Character[] v7 = { cube[5][2][0], cube[5][2][1], cube[5][2][2] };
		Character[] v8 = { cube[5][2][0], cube[5][1][0], cube[5][0][0] };
	
		copy(cube[5][0], v8);
		copy2(cube[5], 2, v5);
		copy(cube[5][2], v6);
		copy2(cube[5], 0, v7);
	}
	
	public static void down_inverted(Character[][][] cube) {
		Character[] v1 = { cube[1][2][0], cube[1][2][1], cube[1][2][2] };
		Character[] v2 = { cube[2][2][0], cube[2][2][1], cube[2][2][2] };
		Character[] v3 = { cube[3][2][0], cube[3][2][1], cube[3][2][2] };
		Character[] v4 = { cube[4][2][0], cube[4][2][1], cube[4][2][2] };
	
		copy(cube[1][2], v2);
		copy(cube[2][2], v3);
		copy(cube[3][2], v4);
		copy(cube[4][2], v1);
	
		Character[] v5 = { cube[5][0][2], cube[5][0][1], cube[5][0][0] };
		Character[] v6 = { cube[5][2][2], cube[5][1][2], cube[5][0][2] };
		Character[] v7 = { cube[5][2][2], cube[5][2][1], cube[5][2][0] };
		Character[] v8 = { cube[5][0][0], cube[5][1][0], cube[5][2][0] };
	
		copy(cube[5][0], v6);
		copy2(cube[5], 2, v7);
		copy(cube[5][2], v8);
		copy2(cube[5], 0, v5);
	}

	public static Estado Aplicar(Reglas R, Character[][][] Est_Act) {
		Character[][][] copia = new Character[6][3][3];

		for (int k = 0; k < 6; ++k) {
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					copia[k][i][j] = Est_Act[k][i][j];
				}
			}
		}

		switch (R) {
			case FRONT_CLOCKWISE:
				front_clockwise(copia);
				break;
			case FRONT_INVERTED:
				front_inverted(copia);
				break;
			case RIGHT_CLOCKWISE:
				right_clockwise(copia);
				break;
			case RIGHT_INVERTED:
				right_inverted(copia);
				break;
			case UP_CLOCKWISE:
				up_clockwise(copia);
				break;
			case UP_INVERTED:
				up_inverted(copia);
				break;
			case BOTTOM_CLOCKWISE:
				bottom_clockwise(copia);
				break;
			case BOTTOM_INVERTED:
				bottom_inverted(copia);
				break;
			case LEFT_CLOCKWISE:
				left_clockwise(copia);
				break;
			case LEFT_INVERTED:
				left_inverted(copia);
				break;
			case DOWN_CLOCKWISE:
				down_clockwise(copia);
				break;
			case DOWN_INVERTED:
				down_inverted(copia);
				break;
			default:
				break;
		}

		Estado Est_Gen = new Estado(copia);

		return Est_Gen;
	}

	public static boolean noEsAncestroDeEnAB(Estado Est_Gen, Estado Est_Act, HashMap<String, Estado> AB) {
		String actual = obtenerClaveDeEstado(Est_Act.getEstado());
		String est_gen = obtenerClaveDeEstado(Est_Gen.getEstado());
		
		while (actual != null) {
			if (actual.equals(est_gen)) {
				return false;
			}
			actual = AB.get(actual).getPadre();
		}
		
		return true;
	}

	public static double Costo(Estado Est_Act, Estado Est_Gen) {
		return 1.0;
	}

	public static double calcular_h(Character[][][] estado, Character[][][] estado_final) {
		double h = 0.0;
		
		/*for (int k = 0; k < 6; ++k) {
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					if (estado[k][i][j] != estado_final[k][i][j]) {
						h += 1.0;
					}
				}
			}
		}*/
		
		return h;
	}

	public static Estado buscarEnAbiertosYCerrados(Estado Est_Gen, HashMap<String, Estado> ABIERTOS_HASH, HashMap<String, Estado> CERRADOS_HASH) {
		
		Estado e = ABIERTOS_HASH.get(obtenerClaveDeEstado(Est_Gen.getEstado()));

		if (e != null) {
			return e;
		}

		return CERRADOS_HASH.get(obtenerClaveDeEstado(Est_Gen.getEstado()));
	}

	public static void Actualizar(PriorityQueue<Estado> ABIERTOS, HashMap<String, Estado> ABIERTOS_HASH, Estado Est_Gen) {
		ABIERTOS.remove(Est_Gen);
		ABIERTOS.add(Est_Gen);
		ABIERTOS_HASH.put(obtenerClaveDeEstado(Est_Gen.getEstado()), Est_Gen);
	}

	public static ArrayList<Character[][][]> a_estrella(Character[][][] estadoInicial, Character[][][] estadoFinal) {
		Estado E_Ini = new Estado(estadoInicial);
		HashMap<String, Estado> AB = new HashMap<>();
		PriorityQueue<Estado> ABIERTOS = new PriorityQueue<>();
		HashMap<String, Estado> ABIERTOS_HASH = new HashMap<>();
		HashMap<String, Estado> CERRADOS_HASH = new HashMap<>();
		boolean EX = false;
		ArrayList<Character[][][]> cam = new ArrayList<>();

		int cont = 0;

		E_Ini.setPadre(null);
		AB.put(obtenerClaveDeEstado(E_Ini.getEstado()), E_Ini);
		ABIERTOS.add(E_Ini);
		ABIERTOS_HASH.put(obtenerClaveDeEstado(E_Ini.getEstado()), E_Ini);
		
		long startTime = System.nanoTime();

		while (!ABIERTOS.isEmpty() && (EX == false)) {
			++cont;
			if (ABIERTOS.size() != ABIERTOS_HASH.size()) {
				System.out.println("HOLA MUNDO!!!!!!!!!!!");
				break;
			}
			Estado Est_Act = ABIERTOS.remove();
			ABIERTOS_HASH.remove(obtenerClaveDeEstado(Est_Act.getEstado()));
			CERRADOS_HASH.put(obtenerClaveDeEstado(Est_Act.getEstado()), Est_Act);
			if (Cond_Term(Est_Act.getEstado())) {
				cam = ConstruirCamino(AB, Est_Act);
				EX = true;
			} else {
				LinkedList<Reglas> RA = Reglas_aplicables(Est_Act.getEstado());
				while (!RA.isEmpty()) {
					Reglas R = RA.pop();
					Estado Est_Gen = Aplicar(R, Est_Act.getEstado());
					if (noEsAncestroDeEnAB(Est_Gen, Est_Act, AB)) {
						double Costo_Temp = Est_Act.getG() + Costo(Est_Act, Est_Gen);
						if (!ABIERTOS_HASH.containsKey(obtenerClaveDeEstado(Est_Gen.getEstado())) && !CERRADOS_HASH.containsKey(obtenerClaveDeEstado(Est_Gen.getEstado()))) {
							Est_Gen.setG(Costo_Temp);
							Est_Gen.setH(calcular_h(Est_Gen.getEstado(), estadoFinal));
							Est_Gen.setF(Est_Gen.getG() + Est_Gen.getH());
							Est_Gen.setPadre(obtenerClaveDeEstado(Est_Act.getEstado()));
							ABIERTOS.add(Est_Gen);
							ABIERTOS_HASH.put(obtenerClaveDeEstado(Est_Gen.getEstado()), Est_Gen);
							AB.put(obtenerClaveDeEstado(Est_Gen.getEstado()), Est_Gen);
						} else {
							Est_Gen = buscarEnAbiertosYCerrados(Est_Gen, ABIERTOS_HASH, CERRADOS_HASH);
							if (Costo_Temp < Est_Gen.getG()) {
								Est_Gen.setG(Costo_Temp);
								Est_Gen.setH(calcular_h(Est_Gen.getEstado(), estadoFinal));
								Est_Gen.setF(Est_Gen.getG() + Est_Gen.getH());
								Est_Gen.setPadre(obtenerClaveDeEstado(Est_Act.getEstado()));
								AB.put(obtenerClaveDeEstado(Est_Gen.getEstado()), Est_Gen);
								if (ABIERTOS_HASH.containsKey(obtenerClaveDeEstado(Est_Gen.getEstado()))) {
									Actualizar(ABIERTOS, ABIERTOS_HASH, Est_Gen);
								}
								if (CERRADOS_HASH.containsKey(obtenerClaveDeEstado(Est_Gen.getEstado()))) {
									CERRADOS_HASH.remove(obtenerClaveDeEstado(Est_Gen.getEstado()));
									ABIERTOS.add(Est_Gen);
									ABIERTOS_HASH.put(obtenerClaveDeEstado(Est_Gen.getEstado()), Est_Gen);
								}
							}
						}
					}
				}
			}
		}

		long endTime = System.nanoTime();
		long duration = endTime - startTime;

		System.out.println("Contador: " + cont);
		System.out.println("Estados explorados: " + (ABIERTOS.size() + CERRADOS_HASH.size()));
		System.out.println("Duracion: " + (duration / 1000000) + " milisegundos");

		if (EX) {
			System.out.println("SI");
		} else {
			System.out.println("NO");
		}

		return cam;
	}
	
	public static void main(String[] args) {
		Character[][][] estadoInicial = inicializarCubo();

		Character[][][] estadoFinal = inicializarCubo();

		front_clockwise(estadoInicial);
		down_clockwise(estadoInicial);
		left_clockwise(estadoInicial);
		bottom_clockwise(estadoInicial);
		right_clockwise(estadoInicial);
		down_clockwise(estadoInicial);

		ArrayList<Character[][][]> cam = a_estrella(estadoInicial, estadoFinal);

		System.out.println("Cantidad de movimientos: "+ (cam.size() - 1));

		for (Character[][][] estado : cam) {
			System.out.println("----------------------------------------------");
			mostrarEstado(estado);
		}
	}
}
