import { ITipoPessoa } from 'app/entities/config/tipo-pessoa/tipo-pessoa.model';
import { IEstadoCivil } from 'app/entities/config/estado-civil/estado-civil.model';
import { IRaca } from 'app/entities/config/raca/raca.model';
import { IReligiao } from 'app/entities/config/religiao/religiao.model';
import { IFoto } from 'app/entities/foto/foto/foto.model';
import { IFotoAvatar } from 'app/entities/foto/foto-avatar/foto-avatar.model';
import { IFotoIcon } from 'app/entities/foto/foto-icon/foto-icon.model';
import { IMensagem } from 'app/entities/user/mensagem/mensagem.model';
import { IAviso } from 'app/entities/user/aviso/aviso.model';
import { IDocumento } from 'app/entities/user/documento/documento.model';
import { IEndereco } from 'app/entities/user/endereco/endereco.model';

export interface IDadosPessoais {
  id?: number;
  nome?: string;
  sobreNome?: string;
  pai?: string | null;
  mae?: string;
  telefone?: string | null;
  celular?: string;
  whatsapp?: string | null;
  email?: string;
  tipoPessoa?: ITipoPessoa;
  estadoCivil?: IEstadoCivil;
  raca?: IRaca;
  religiao?: IReligiao;
  foto?: IFoto | null;
  fotoAvatar?: IFotoAvatar | null;
  fotoIcon?: IFotoIcon | null;
  mensagems?: IMensagem[] | null;
  avisos?: IAviso[] | null;
  documentos?: IDocumento[] | null;
  enderecos?: IEndereco[] | null;
}

export class DadosPessoais implements IDadosPessoais {
  constructor(
    public id?: number,
    public nome?: string,
    public sobreNome?: string,
    public pai?: string | null,
    public mae?: string,
    public telefone?: string | null,
    public celular?: string,
    public whatsapp?: string | null,
    public email?: string,
    public tipoPessoa?: ITipoPessoa,
    public estadoCivil?: IEstadoCivil,
    public raca?: IRaca,
    public religiao?: IReligiao,
    public foto?: IFoto | null,
    public fotoAvatar?: IFotoAvatar | null,
    public fotoIcon?: IFotoIcon | null,
    public mensagems?: IMensagem[] | null,
    public avisos?: IAviso[] | null,
    public documentos?: IDocumento[] | null,
    public enderecos?: IEndereco[] | null
  ) {}
}

export function getDadosPessoaisIdentifier(dadosPessoais: IDadosPessoais): number | undefined {
  return dadosPessoais.id;
}
