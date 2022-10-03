import { ISaidaEstoque } from 'app/entities/controle/saida-estoque/saida-estoque.model';

export interface IFotoSaidaEstoque {
  id?: number;
  conteudoContentType?: string;
  conteudo?: string;
  saidaEstoque?: ISaidaEstoque | null;
}

export class FotoSaidaEstoque implements IFotoSaidaEstoque {
  constructor(
    public id?: number,
    public conteudoContentType?: string,
    public conteudo?: string,
    public saidaEstoque?: ISaidaEstoque | null
  ) {}
}

export function getFotoSaidaEstoqueIdentifier(fotoSaidaEstoque: IFotoSaidaEstoque): number | undefined {
  return fotoSaidaEstoque.id;
}
