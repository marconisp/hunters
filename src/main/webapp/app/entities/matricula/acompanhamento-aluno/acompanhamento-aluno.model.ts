import { IItemMateria } from 'app/entities/matricula/item-materia/item-materia.model';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { Ensino } from 'app/entities/enumerations/ensino.model';

export interface IAcompanhamentoAluno {
  id?: number;
  ano?: number;
  ensino?: Ensino;
  bimestre?: number;
  itemMaterias?: IItemMateria[] | null;
  dadosPessoais?: IDadosPessoais | null;
}

export class AcompanhamentoAluno implements IAcompanhamentoAluno {
  constructor(
    public id?: number,
    public ano?: number,
    public ensino?: Ensino,
    public bimestre?: number,
    public itemMaterias?: IItemMateria[] | null,
    public dadosPessoais?: IDadosPessoais | null
  ) {}
}

export function getAcompanhamentoAlunoIdentifier(acompanhamentoAluno: IAcompanhamentoAluno): number | undefined {
  return acompanhamentoAluno.id;
}
