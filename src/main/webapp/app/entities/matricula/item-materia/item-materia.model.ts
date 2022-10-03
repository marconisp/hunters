import { IMateria } from 'app/entities/matricula/materia/materia.model';
import { IAcompanhamentoAluno } from 'app/entities/matricula/acompanhamento-aluno/acompanhamento-aluno.model';

export interface IItemMateria {
  id?: number;
  nota?: string;
  obs?: string | null;
  materia?: IMateria | null;
  acompanhamentoAluno?: IAcompanhamentoAluno | null;
}

export class ItemMateria implements IItemMateria {
  constructor(
    public id?: number,
    public nota?: string,
    public obs?: string | null,
    public materia?: IMateria | null,
    public acompanhamentoAluno?: IAcompanhamentoAluno | null
  ) {}
}

export function getItemMateriaIdentifier(itemMateria: IItemMateria): number | undefined {
  return itemMateria.id;
}
