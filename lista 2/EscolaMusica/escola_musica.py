from enum import Enum


class Instrumento(Enum):
    GUITARRA = "Guitarra"
    BAIXO = "Baixo"
    BATERIA = "Bateria"
    TECLADO = "Teclado"
    VIOLAO = "Violão"
    SAXOFONE = "Saxofone"


class Musico:
    def __init__(self, nome: str, idade: int):
        if idade < 0:
            raise ValueError("A idade não pode ser negativa.")
        self.nome = nome
        self.idade = idade
        self.instrumentos: list[Instrumento] = []

    def adicionar_instrumento(self, instrumento: Instrumento) -> bool:
        """Adiciona até dois instrumentos distintos ao músico."""
        if not isinstance(instrumento, Instrumento):
            raise TypeError("O instrumento deve ser um valor de Instrumento.")
        if len(self.instrumentos) >= 2 or instrumento in self.instrumentos:
            return False
        self.instrumentos.append(instrumento)
        return True

    def __str__(self) -> str:
        nomes = ", ".join(instrumento.value for instrumento in self.instrumentos)
        return f"{self.nome} ({nomes or 'sem instrumentos cadastrados'})"


class Banda:
    def __init__(self, nome: str):
        self.nome = nome
        self.musicos: list[Musico] = []

    def adicionar_musico(self, musico: Musico) -> None:
        if not isinstance(musico, Musico):
            raise TypeError("A banda só pode receber objetos Musico.")
        if musico not in self.musicos:
            self.musicos.append(musico)

    def instrumentos_presentes(self) -> set[Instrumento]:
        return {
            instrumento
            for musico in self.musicos
            for instrumento in musico.instrumentos
        }


class EscolaMusica:
    QUANTIDADE_BANDAS = 3

    def __init__(self):
        self.alunos: list[Musico] = []
        self.bandas: list[Banda] = []

    def adicionar_musico(self, musico: Musico) -> None:
        if not isinstance(musico, Musico):
            raise TypeError("A escola só pode cadastrar objetos Musico.")
        if musico not in self.alunos:
            self.alunos.append(musico)

    def formar_bandas(self) -> None:
        """Distribui músicos minimizando sobreposição de instrumentos.

        Primeiro são preferidas bandas sem instrumentos em comum com o músico,
        o que equivale a minimizar a sobreposição. Em caso de empate, escolhe
        a banda com menos integrantes; persistindo o empate, usa a primeira.
        """
        self.bandas = [
            Banda(f"Banda {numero}")
            for numero in range(1, self.QUANTIDADE_BANDAS + 1)
        ]

        for musico in self.alunos:
            instrumentos_musico = set(musico.instrumentos)
            banda_escolhida = min(
                self.bandas,
                key=lambda banda: (
                    len(instrumentos_musico & banda.instrumentos_presentes()),
                    len(banda.musicos),
                ),
            )
            banda_escolhida.adicionar_musico(musico)


def main() -> None:
    escola = EscolaMusica()

    # Músicos de demonstração: altere ou acrescente nomes e instrumentos
    # para experimentar outras distribuições.
    cadastros = [
        ("Ana", 17, (Instrumento.GUITARRA, Instrumento.BAIXO)),
        ("Bruno", 19, (Instrumento.BATERIA, Instrumento.TECLADO)),
        ("Carla", 18, (Instrumento.VIOLAO, Instrumento.SAXOFONE)),
        ("Diego", 20, (Instrumento.GUITARRA, Instrumento.TECLADO)),
        ("Elisa", 16, (Instrumento.BAIXO, Instrumento.BATERIA)),
        ("Felipe", 21, (Instrumento.VIOLAO, Instrumento.GUITARRA)),
        ("Giovana", 18, (Instrumento.SAXOFONE, Instrumento.TECLADO)),
        ("Hugo", 22, (Instrumento.BATERIA, Instrumento.VIOLAO)),
        ("Isabela", 17, (Instrumento.BAIXO, Instrumento.SAXOFONE)),
    ]

    for nome, idade, instrumentos in cadastros:
        musico = Musico(nome, idade)
        for instrumento in instrumentos:
            musico.adicionar_instrumento(instrumento)
        escola.adicionar_musico(musico)

    escola.formar_bandas()

    for banda in escola.bandas:
        print(f"\n{banda.nome}:")
        if not banda.musicos:
            print("  (sem integrantes)")
        for musico in banda.musicos:
            instrumentos = ", ".join(
                instrumento.value for instrumento in musico.instrumentos
            )
            print(f"  {musico.nome} — {instrumentos}")


if __name__ == "__main__":
    main()
