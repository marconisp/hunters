import dayjs from 'dayjs/esm';
import { IProduto } from 'app/entities/controle/produto/produto.model';
import { IFotoSaidaEstoque } from 'app/entities/foto/foto-saida-estoque/foto-saida-estoque.model';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';

export interface ISaidaEstoque {
  id?: number;
  data?: dayjs.Dayjs;
  qtde?: number;
  valorUnitario?: number;
  obs?: string | null;
  produto?: IProduto | null;
  fotoSaidaEstoques?: IFotoSaidaEstoque[] | null;
  dadosPessoais?: IDadosPessoais | null;
}

export class SaidaEstoque implements ISaidaEstoque {
  constructor(
    public id?: number,
    public data?: dayjs.Dayjs,
    public qtde?: number,
    public valorUnitario?: number,
    public obs?: string | null,
    public produto?: IProduto | null,
    public fotoSaidaEstoques?: IFotoSaidaEstoque[] | null,
    public dadosPessoais?: IDadosPessoais | null
  ) {}
}

export function getSaidaEstoqueIdentifier(saidaEstoque: ISaidaEstoque): number | undefined {
  return saidaEstoque.id;
}
