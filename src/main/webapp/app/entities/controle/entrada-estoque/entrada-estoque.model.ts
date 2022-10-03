import dayjs from 'dayjs/esm';
import { IProduto } from 'app/entities/controle/produto/produto.model';
import { IFotoEntradaEstoque } from 'app/entities/foto/foto-entrada-estoque/foto-entrada-estoque.model';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';

export interface IEntradaEstoque {
  id?: number;
  data?: dayjs.Dayjs;
  qtde?: number;
  valorUnitario?: number;
  obs?: string | null;
  produto?: IProduto | null;
  fotoEntradaEstoques?: IFotoEntradaEstoque[] | null;
  dadosPessoais?: IDadosPessoais | null;
}

export class EntradaEstoque implements IEntradaEstoque {
  constructor(
    public id?: number,
    public data?: dayjs.Dayjs,
    public qtde?: number,
    public valorUnitario?: number,
    public obs?: string | null,
    public produto?: IProduto | null,
    public fotoEntradaEstoques?: IFotoEntradaEstoque[] | null,
    public dadosPessoais?: IDadosPessoais | null
  ) {}
}

export function getEntradaEstoqueIdentifier(entradaEstoque: IEntradaEstoque): number | undefined {
  return entradaEstoque.id;
}
