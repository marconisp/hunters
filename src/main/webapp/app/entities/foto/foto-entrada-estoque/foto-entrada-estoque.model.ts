import { IEntradaEstoque } from 'app/entities/controle/entrada-estoque/entrada-estoque.model';

export interface IFotoEntradaEstoque {
  id?: number;
  conteudoContentType?: string;
  conteudo?: string;
  entradaEstoque?: IEntradaEstoque | null;
}

export class FotoEntradaEstoque implements IFotoEntradaEstoque {
  constructor(
    public id?: number,
    public conteudoContentType?: string,
    public conteudo?: string,
    public entradaEstoque?: IEntradaEstoque | null
  ) {}
}

export function getFotoEntradaEstoqueIdentifier(fotoEntradaEstoque: IFotoEntradaEstoque): number | undefined {
  return fotoEntradaEstoque.id;
}
